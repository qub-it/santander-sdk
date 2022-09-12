package org.fenixedu.santandersdk.services;

import javax.xml.ws.BindingProvider;

import com.qubit.solution.fenixedu.bennu.webservices.services.client.BennuWebServiceClient;

import pt.sibscartoes.portal.wcf.ITUIDetailService;
import pt.sibscartoes.portal.wcf.TUIDetailService;

public class TuiWebserviceClient extends BennuWebServiceClient<ITUIDetailService> {

    public ITUIDetailService getTuiDetailService() {
        return getClient();
    }

    @Override
    protected BindingProvider getService() {
        return (BindingProvider) new TUIDetailService().getTUIDetailWsHttp();
    }

}
