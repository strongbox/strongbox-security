package org.carlspring.strongbox.rest;

import org.carlspring.strongbox.configuration.GenericLDAPConfiguration;
import org.carlspring.strongbox.configuration.AttributeMappings;
import org.carlspring.strongbox.security.jaas.authentication.AuthenticationException;
import org.carlspring.strongbox.services.GenericLDAPConfigurationManagementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;

/**
 * @author mtodorov
 */
public abstract class AbstractLDAPConfigurationManagementRestlet<T extends GenericLDAPConfiguration>
        extends BaseRestlet
{


    @PUT
    @Path("/xml")
    public Response setConfiguration(T configuration)
            throws IOException,
                   AuthenticationException,
                   JAXBException
    {
        getConfigurationManagementService().setConfiguration(configuration);

        getLogger().info("Received new LDAP configuration over REST.");

        return Response.ok().build();
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public Response getConfiguration()
            throws IOException, ParseException
    {
        getLogger().debug("Received LDAP configuration request.");

        return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getConfiguration()).build();
    }

    @GET
    @Path("/host")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHost()
    {
        if (getConfigurationManagementService().getHost() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getHost()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Host not yet defined.").build();
        }
    }

    @PUT
    @Path("/host/{host:.*}")
    public Response setHost(@PathParam("host") String host)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setHost(host);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/port")
    @Produces(MediaType.TEXT_PLAIN)
    public int getPort()
    {
        return getConfigurationManagementService().getPort();
    }

    @PUT
    @Path("/port/{port}")
    public Response setPort(@PathParam("port") int port)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setPort(port);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/protocol")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProtocol()
    {
        if (getConfigurationManagementService().getProtocol() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getProtocol()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Protocol not yet defined.").build();
        }
    }

    @PUT
    @Path("/protocol/{protocol}")
    public Response setProtocol(@PathParam("protocol") String protocol)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setProtocol(protocol);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/username")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUsername()
    {
        if (getConfigurationManagementService().getUsername() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getUsername()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Username not yet defined.").build();
        }
    }

    @PUT
    @Path("/username/{username}")
    public Response setUsername(@PathParam("username") String username)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setUsername(username);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/password/{password:.*}")
    public Response setPassword(@PathParam("password") String password)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setPassword(password);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/authentication-type")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAuthenticationType()
    {
        if (getConfigurationManagementService().getAuthenticationType() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getAuthenticationType()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Authentication type not yet defined.").build();
        }
    }

    @PUT
    @Path("/authentication-type/{type:.*}")
    public Response setAuthenticationType(@PathParam("type") String authenticationType)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setAuthenticationType(authenticationType);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/root-dn")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getRootDn()
    {
        if (getConfigurationManagementService().getRootDn() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getRootDn()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Root DN not yet defined.").build();
        }
    }

    @PUT
    @Path("/root-dn/{dn:.*}")
    public Response setRootDn(@PathParam("dn") String rootDn)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setRootDn(rootDn);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/timeout")
    @Produces(MediaType.TEXT_PLAIN)
    public int getTimeout()
    {
        return getConfigurationManagementService().getTimeout();
    }

    @PUT
    @Path("/timeout/{timeout}")
    public Response setTimeout(@PathParam("timeout") int timeout)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setTimeout(timeout);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/domain")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDomain()
    {
        if (getConfigurationManagementService().getDomain() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getDomain()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Domain not yet defined.").build();
        }
    }

    @PUT
    @Path("/domain/{domain:.*}")
    public Response setDomain(@PathParam("domain") String domain)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setDomain(domain);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/login-mode")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getLoginMode()
    {
        if (getConfigurationManagementService().getLoginMode() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getLoginMode()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Login mode settings not yet defined.").build();
        }
    }

    @PUT
    @Path("/login-mode/{mode:.*}")
    public Response setLoginMode(@PathParam("mode") String loginMode)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setLoginMode(loginMode);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/attribute-mappings")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAttributeMappings()
    {
        if (getConfigurationManagementService().getAttributeMappings() != null)
        {
            return Response.status(Response.Status.OK).entity(getConfigurationManagementService().getAttributeMappings()).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Attribute settings not yet defined.").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/attribute-mappings")
    public Response setAttributeMappings(AttributeMappings attributeMappings)
            throws IOException, JAXBException
    {
        try
        {
            getConfigurationManagementService().setAttributeMappings(attributeMappings);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            getLogger().error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public abstract GenericLDAPConfigurationManagementService getConfigurationManagementService();

    public abstract Logger getLogger();
    
}
