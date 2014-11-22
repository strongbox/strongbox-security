package org.carlspring.strongbox.rest;

import org.carlspring.strongbox.configuration.AttributeMappings;
import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.security.jaas.authentication.AuthenticationException;
import org.carlspring.strongbox.services.LDAPConfigurationManagementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
@Path("/configuration/ldap")
public class LDAPConfigurationManagementRestlet
        extends BaseRestlet
{

    private static final Logger logger = LoggerFactory.getLogger(LDAPConfigurationManagementRestlet.class);

    @Autowired
    private LDAPConfigurationManagementService ldapConfigurationManagementService;


    @PUT
    @Path("/xml")
    public Response setConfiguration(LDAPConfiguration configuration)
            throws IOException,
                   AuthenticationException,
                   JAXBException
    {
        ldapConfigurationManagementService.setConfiguration(configuration);

        logger.info("Received new LDAP configuration over REST.");

        return Response.ok().build();
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public Response getConfiguration()
            throws IOException, ParseException
    {
        logger.debug("Received LDAP configuration request.");

        return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getConfiguration()).build();
    }

    @GET
    @Path("/host")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHost()
    {
        if (ldapConfigurationManagementService.getHost() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getHost()).build();
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
            ldapConfigurationManagementService.setHost(host);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/port")
    @Produces(MediaType.TEXT_PLAIN)
    public int getPort()
    {
        return ldapConfigurationManagementService.getPort();
    }

    @PUT
    @Path("/port/{port}")
    public Response setPort(@PathParam("port") int port)
            throws IOException, JAXBException
    {
        try
        {
            ldapConfigurationManagementService.setPort(port);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/protocol")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProtocol()
    {
        if (ldapConfigurationManagementService.getProtocol() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getProtocol()).build();
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
            ldapConfigurationManagementService.setProtocol(protocol);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/username")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUsername()
    {
        if (ldapConfigurationManagementService.getUsername() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getUsername()).build();
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
            ldapConfigurationManagementService.setUsername(username);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

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
            ldapConfigurationManagementService.setPassword(password);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/authentication-type")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAuthenticationType()
    {
        if (ldapConfigurationManagementService.getAuthenticationType() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getAuthenticationType()).build();
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
            ldapConfigurationManagementService.setAuthenticationType(authenticationType);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/root-dn")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getRootDn()
    {
        if (ldapConfigurationManagementService.getRootDn() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getRootDn()).build();
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
            ldapConfigurationManagementService.setRootDn(rootDn);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/timeout")
    @Produces(MediaType.TEXT_PLAIN)
    public int getTimeout()
    {
        return ldapConfigurationManagementService.getTimeout();
    }

    @PUT
    @Path("/timeout/{timeout}")
    public Response setTimeout(@PathParam("timeout") int timeout)
            throws IOException, JAXBException
    {
        try
        {
            ldapConfigurationManagementService.setTimeout(timeout);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/domain")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDomain()
    {
        if (ldapConfigurationManagementService.getDomain() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getDomain()).build();
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
            ldapConfigurationManagementService.setDomain(domain);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/login-mode")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getLoginMode()
    {
        if (ldapConfigurationManagementService.getLoginMode() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getLoginMode()).build();
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
            ldapConfigurationManagementService.setLoginMode(loginMode);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/attribute-mappings")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAttributeMappings()
    {
        if (ldapConfigurationManagementService.getAttributeMappings() != null)
        {
            return Response.status(Response.Status.OK).entity(ldapConfigurationManagementService.getAttributeMappings()).build();
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
            ldapConfigurationManagementService.setAttributeMappings(attributeMappings);

            return Response.status(Response.Status.OK).build();
        }
        catch (IOException | JAXBException e)
        {
            logger.error(e.getMessage(), e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
