package embinmc.javaengine.resource;

public enum ResourceType {
    NEITHER("neither", ""),
    ASSET("asset", "assets/"),
    DATA("data", "data/");

    private final String dir;
    private final String name;

    ResourceType(String name, String dir) {
        this.dir = dir;
        this.name = name;
    }

    public String getDir() {
        return this.dir;
    }

    public static ResourceType getTypeOfPath(String path) {
        String newPath = path.replaceAll("\\\\", "/");
        if (newPath.startsWith(ResourceType.ASSET.getDir())) return ResourceType.ASSET;
        if (newPath.startsWith(ResourceType.DATA.getDir())) return ResourceType.DATA;
        return ResourceType.NEITHER;
    }

    public String pathFromIdentifier(Identifier identifier) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getDir());
        builder.append(identifier.getNamespace());
        builder.append('/');
        builder.append(identifier.getPath());
        return builder.toString();
    }

    public String getName() { // i don't NEED this but too bad
        return this.name;
    }

    @Override
    public String toString() {
        return this.getDir();
    }
}
