package com.interviews.questions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class ReadXmlFileTest {

    @Test
    @Disabled
    void test() throws Exception {
//        File file = new File("C:\\Users\\eliezerr\\Downloads\\climent (1)\\spring-shell-reactive\\pom.xml");
//        File file = new File("C:\\Users\\eliezerr\\IdeaProjects\\intervies-questions\\pom.xml");
        File file = new File("C:\\Users\\eliezerr\\IdeaProjects\\saas-platform-lib-java17\\binary-file-access\\impl\\pom.xml");


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        String textContent = doc.getElementsByTagName("name").item(0).getTextContent();

        Node parentNode = doc.getElementsByTagName("parent").item(0);
        if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
            Element parentElement = (Element) parentNode;
            String parentGroupId = parentElement.getElementsByTagName("groupId").item(0).getTextContent();
            String parentArtifactId = parentElement.getElementsByTagName("artifactId").item(0).getTextContent();
            String parentVersion = parentElement.getElementsByTagName("version").item(0).getTextContent();
        }

        Node groupIdNode = doc.getElementsByTagName("groupId").item(1);
        String groupIdValue = "${project.groupId}";
        Set<String> modules = Set.of("binary-file-access", "security-manager");// extractModules(doc);
        Set<Dependency> dependencies = extractDependencies(doc).stream()
                .filter(dependency -> dependency.groupId.equals(groupIdValue)
                        && modules.stream().anyMatch(dependency.artifactId::contains)
                )
                .collect(Collectors.toSet());
        NodeList name = doc.getElementsByTagName("name");
    }

    private record Dependency(String groupId, String artifactId) {
    }

    private static Set<Dependency> extractDependencies(Document doc) {
        Set<Dependency> dependencies = new HashSet<>();
        NodeList dependenciesNode = doc.getElementsByTagName("dependency");
        for (int i = 0; i < dependenciesNode.getLength(); i++) {
            Node dependencyNode = dependenciesNode.item(i);
            if (dependencyNode.getNodeType() == Node.ELEMENT_NODE) {
                Element dependencyElement = (Element) dependencyNode;
                String groupId = dependencyElement.getElementsByTagName("groupId").item(0).getTextContent();
                String artifactId = dependencyElement.getElementsByTagName("artifactId").item(0).getTextContent();
                dependencies.add(new Dependency(groupId, artifactId));
            }
        }
        return dependencies;
    }

    private static Set<String> extractModules(Document doc) {
        Set<String> modules = new HashSet<>();
        NodeList moduleNodes = doc.getElementsByTagName("module");
        for (int i = 0; i < moduleNodes.getLength(); i++) {
            Node moduleNode = moduleNodes.item(i);
            String moduleName = moduleNode.getTextContent();
            modules.add(moduleName);
        }
        return modules;
    }

    @Test
    void mapTest() {
        Map<String, Set<String>> map = new HashMap<>();
        String value1 = "value1";
        map.computeIfAbsent("1", v -> new HashSet<>()).add(value1);
        String value2 = "value2";
        map.computeIfAbsent("2", v -> new HashSet<>()).add(value2);

        System.out.println(map);
    }

}
