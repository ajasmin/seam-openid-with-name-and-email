package org.jboss.seam.example.openid;


import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.Component;
import org.jboss.seam.navigation.Pages;
import org.jboss.seam.log.*;

/**
 * Modified version of org.jboss.seam.security.openid.OpenIdPhaseListener
 * This version has all mentions of the OpenId class replaced by OpenIdAx
 */
@SuppressWarnings("serial")
public class OpenIdPhaseListenerAx
    implements PhaseListener
{
    private transient LogProvider log = Logging.getLogProvider(OpenIdPhaseListenerAx.class);

    @SuppressWarnings("unchecked")
    public void beforePhase(PhaseEvent event)
    {
        String viewId = Pages.getCurrentViewId();

        if (viewId==null || !viewId.startsWith("/openid.")) {
            return;
        }
        
        OpenIdAx open = (OpenIdAx) Component.getInstance(OpenIdAx.class);
        if (open.getId() == null) {
            try {
                sendXRDS();
            } catch (IOException e) {
		log.warn(e);
            }
            return;
        }

        OpenIdAx openid = (OpenIdAx) Component.getInstance(OpenIdAx.class);
        
        openid.verify();
        
        Pages.handleOutcome(event.getFacesContext(), null, "/openid.xhtml");
    }



    public void sendXRDS()
        throws IOException
    {
        FacesContext        context    = FacesContext.getCurrentInstance();
        ExternalContext     extContext = context.getExternalContext();
        HttpServletResponse response   = (HttpServletResponse) extContext.getResponse();

        response.setContentType("application/xrds+xml");
        PrintWriter out = response.getWriter();

        // XXX ENCODE THE URL!
        OpenIdAx open = (OpenIdAx) Component.getInstance(OpenIdAx.class);

        out.println("<XRDS xmlns=\"xri://$xrd*($v*2.0)\"><XRD><Service>" +
                    "<Type>http://specs.openid.net/auth/2.0/return_to</Type><URI>" +
                    open.returnToUrl() + "</URI></Service></XRD></XRDS>");

        context.responseComplete();
    }


    public void afterPhase(PhaseEvent event) {
    }

    public PhaseId getPhaseId() 
    {
        return PhaseId.RENDER_RESPONSE;
    }
}

