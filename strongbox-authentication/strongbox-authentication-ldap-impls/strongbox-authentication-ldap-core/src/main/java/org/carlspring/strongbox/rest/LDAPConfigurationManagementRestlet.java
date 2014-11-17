package org.carlspring.strongbox.rest;

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

}
