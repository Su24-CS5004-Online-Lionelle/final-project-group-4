import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class JavaToMermaid {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the path to the directory containing Java files.");
            return;
        }

        String directoryPath = args[0];
        List<File> javaFiles = listJavaFiles(new File(directoryPath));

        TreeSet<String> classDefinitions = new TreeSet<>();
        TreeSet<String> relationships = new TreeSet<>();

        for (File javaFile : javaFiles) {
            try (FileInputStream in = new FileInputStream(javaFile)) {
                CompilationUnit cu = StaticJavaParser.parse(in);
                cu.accept(new ClassVisitor(classDefinitions, relationships), null);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + javaFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (com.github.javaparser.ParseProblemException e) {
                System.out.println("Parsing problem: " + e.getMessage());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("```mermaid\n");
        sb.append("classDiagram\n");

        for (String classDef : classDefinitions) {
            sb.append(classDef);
        }

        for (String relationship : relationships) {
            sb.append(relationship);
        }

        sb.append("```");
        System.out.println(sb.toString());

        // Write the output to a file
        try {
            Files.write(Paths.get("output.mmd"), sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<File> listJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    javaFiles.addAll(listJavaFiles(file));
                } else if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
        return javaFiles;
    }

    private static class ClassVisitor extends VoidVisitorAdapter<Void> {
        private TreeSet<String> classDefinitions;
        private TreeSet<String> relationships;

        public ClassVisitor(TreeSet<String> classDefinitions, TreeSet<String> relationships) {
            this.classDefinitions = classDefinitions;
            this.relationships = relationships;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            super.visit(n, arg);
            StringBuilder sb = new StringBuilder();
            sb.append("class ").append(n.getNameAsString()).append(" {\n");

            n.getMembers().stream().filter(member -> member instanceof MethodDeclaration)
                    .map(member -> (MethodDeclaration) member)
                    .sorted((m1, m2) -> m1.getNameAsString().compareTo(m2.getNameAsString()))
                    .forEach(method -> sb.append("  + ").append(method.getNameAsString())
                            .append("()\n"));

            n.getMembers().stream().filter(member -> member instanceof FieldDeclaration)
                    .map(member -> (FieldDeclaration) member)
                    .sorted((f1, f2) -> f1.getVariable(0).getNameAsString()
                            .compareTo(f2.getVariable(0).getNameAsString()))
                    .forEach(field -> field.getVariables().forEach(variable -> sb.append("  - ")
                            .append(variable.getNameAsString()).append("\n")));

            sb.append("}\n");
            classDefinitions.add(sb.toString());

            if (n.getExtendedTypes().isNonEmpty()) {
                for (ClassOrInterfaceType extendedType : n.getExtendedTypes()) {
                    relationships.add(
                            n.getNameAsString() + " --|> " + extendedType.getNameAsString() + "\n");
                }
            }

            for (FieldDeclaration field : n.getFields()) {
                if (field.getElementType() instanceof ClassOrInterfaceType) {
                    ClassOrInterfaceType fieldType = (ClassOrInterfaceType) field.getElementType();
                    relationships.add(
                            n.getNameAsString() + " --> " + fieldType.getNameAsString() + "\n");
                }
            }
        }
    }
}
