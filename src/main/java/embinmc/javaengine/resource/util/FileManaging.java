package embinmc.javaengine.resource.util;

import com.google.gson.JsonObject;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

import java.io.File;
import java.util.*;

public final class FileManaging {
    private static final Logger LOGGER = Util.getLogger();

    public static List<String> getPathsInFolder(String folder) {
        File folder2 = new File(folder);
        List<String> paths = new ArrayList<>(32);
        if (folder2.isDirectory()) {
            for (File file : folder2.listFiles()) {
                if (file.isDirectory()) {
                    paths.addAll(getPathsInFolder(file.getPath()));
                } else {
                    paths.add(file.getPath());
                }
            }
            return paths;
        } else {
            LOGGER.warn("'{}' is not a folder", folder);
            return List.of();
        }
    }

    public static String removeBaseFromFile(String base, String file) {
        String newFile = file.replaceAll("\\\\", "/");
        return newFile.replaceFirst(base, "");
    }

    public static Map<String, String> removeBaseFromFiles(String base, List<String> files) {
        Map<String, String> mappedPaths = HashMap.newHashMap(files.size());
        for (String path : files) {
            mappedPaths.put(path, FileManaging.removeBaseFromFile(base, path));
        }
        return mappedPaths;
    }

    public static List<String> getNamespacesInGeneric(String folder) {
        List<String> namespaces = new ArrayList<>(8);
        File genFolder = new File(folder + "/");
        if (!genFolder.exists()) {
            LOGGER.warn("'{}' folder doesn't exist and does not have namespaces", folder);
            return List.of();
        }
        if (!genFolder.isDirectory()) throw new RuntimeException("'" + folder + "' is not a folder");
        for (File file : genFolder.listFiles()) {
            String potentialNamespace = file.getName();
            if (file.isDirectory() && Identifier.isNamespaceValid(potentialNamespace)) {
                namespaces.add(potentialNamespace);
            }
        }
        return namespaces;
    }

    public static List<String> getNamespacesInAssets() {
        return getNamespacesInGeneric("assets");
    }

    public static List<String> getNamespacesInData() {
        return getNamespacesInGeneric("data");
    }

    public static JsonObject getJsonFile(String path) {
        try {
            return GsonUtil.fromInputStream(new File(path).toURL().openStream());
        } catch (Exception e) {
            throw new RuntimeException("What the freak is " + path);
        }
    }

    public static List<String> getNamespacesWithFolder(String folder) {
        List<String> foundNamespaces = new ArrayList<>(FileManaging.getNamespacesInData().size());
        for (String namespace : FileManaging.getNamespacesInData()) {
            File namespaceFolder = new File("data/" + namespace);
            for (File content : namespaceFolder.listFiles()) {
                if (content.isDirectory() && Objects.equals(content.getName(), folder)) {
                    foundNamespaces.add(namespace);
                    break;
                }
            }
        }
        return foundNamespaces;
    }

    @Deprecated
    public static String removeBaseFromFileLiteral(String base, String file) {
        return file.replaceFirst(base, "");
    }

    @Deprecated
    public static Map<String, String> removeBaseFromFilesLiteral(String base, List<String> files) {
        Map<String, String> mappedPaths = HashMap.newHashMap(files.size());
        for (String path : files) {
            mappedPaths.put(path, FileManaging.removeBaseFromFileLiteral(base, path));
        }
        return mappedPaths;
    }
}
