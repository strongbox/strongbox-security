

Principals
-------------------------------------------------------------------------------------------

    org.carlspring.strongbox.jaas.principal.UserPrincipal


Roles
-------------------------------------------------------------------------------------------

The possible roles are:

    ADMINISTRATOR
    USER
    ANONYMOUS


Access Control List
-------------------------------------------------------------------------------------------

The class java.security.acl.Group extends Principal.
- Groups have members.
- Group members are Principals.
- In JAAS Roles are represented by Principals.

On the server-side, the REST module should have restrictions for the paths using annotations like:

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    @RolesAllowed({"groupXYZ"})
    public Response getEvent(@Context SecurityContext sc,
                             @PathParam("id") long id)
    {
        log.debug("Auth: " + sc.getAuthenticationScheme());
        log.debug("User: " + sc.getUserPrincipal().getName()); // The username!
        log.debug("Admin-privileges: " + sc.isUserInRole("groupXYZ"));

        return Response.ok(“auth success”).build();
    }

