package org.carlspring.strongbox.security.authorization.el;

/**
 * @author mtodorov
 */
public class AuthorizationExpression
{

    private String rawRepresentation;

    private String[] storagePatternIncludes;
    private String[] storagePatternExcludes;

    private String[] repositoryPatternIncludes;
    private String[] repositoryPatternExcludes;

    private String[] pathPatternIncludes;
    private String[] pathPatternExcludes;

    private String[] permissionsIncludes;
    private String[] permissionsExcludes;


    public AuthorizationExpression()
    {
    }

    public AuthorizationExpression(String rawRepresentation)
    {
        this.rawRepresentation = rawRepresentation;
    }

    public String getRawRepresentation()
    {
        return rawRepresentation;
    }

    public void setRawRepresentation(String rawRepresentation)
    {
        this.rawRepresentation = rawRepresentation;
    }

    public String[] getStoragePatternIncludes()
    {
        return storagePatternIncludes;
    }

    public void setStoragePatternIncludes(String[] storagePatternIncludes)
    {
        this.storagePatternIncludes = storagePatternIncludes;
    }

    public String[] getStoragePatternExcludes()
    {
        return storagePatternExcludes;
    }

    public void setStoragePatternExcludes(String[] storagePatternExcludes)
    {
        this.storagePatternExcludes = storagePatternExcludes;
    }

    public String[] getRepositoryPatternIncludes()
    {
        return repositoryPatternIncludes;
    }

    public void setRepositoryPatternIncludes(String[] repositoryPatternIncludes)
    {
        this.repositoryPatternIncludes = repositoryPatternIncludes;
    }

    public String[] getRepositoryPatternExcludes()
    {
        return repositoryPatternExcludes;
    }

    public void setRepositoryPatternExcludes(String[] repositoryPatternExcludes)
    {
        this.repositoryPatternExcludes = repositoryPatternExcludes;
    }

    public String[] getPathPatternIncludes()
    {
        return pathPatternIncludes;
    }

    public void setPathPatternIncludes(String[] pathPatternIncludes)
    {
        this.pathPatternIncludes = pathPatternIncludes;
    }

    public String[] getPathPatternExcludes()
    {
        return pathPatternExcludes;
    }

    public void setPathPatternExcludes(String[] pathPatternExcludes)
    {
        this.pathPatternExcludes = pathPatternExcludes;
    }

    public String[] getPermissionsIncludes()
    {
        return permissionsIncludes;
    }

    public void setPermissionsIncludes(String[] permissionsIncludes)
    {
        this.permissionsIncludes = permissionsIncludes;
    }

    public String[] getPermissionsExcludes()
    {
        return permissionsExcludes;
    }

    public void setPermissionsExcludes(String[] permissionsExcludes)
    {
        this.permissionsExcludes = permissionsExcludes;
    }

}
