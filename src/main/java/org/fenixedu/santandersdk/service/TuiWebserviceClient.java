package org.fenixedu.santandersdk.service;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.AddressingFeature;

import com.qubit.solution.fenixedu.bennu.webservices.services.client.BennuWebServiceClient;

import pt.sibscartoes.portal.wcf.ITUIDetailService;
import pt.sibscartoes.portal.wcf.TUIDetailService;

public class TuiWebserviceClient extends BennuWebServiceClient<ITUIDetailService> {

    public ITUIDetailService getTuiDetailService() {
        return getClient();
    }

    @Override
    protected BindingProvider getService() {
        AddressingFeature addressingFeature = new AddressingFeature(true, true);
        return (BindingProvider) new TUIDetailService().getTUIDetailWsHttp(addressingFeature);
    }

}
