package org.fenixedu.santandersdk.services;

import javax.xml.ws.BindingProvider;

import com.qubit.solution.fenixedu.bennu.webservices.services.client.BennuWebServiceClient;

import pt.sibscartoes.portal.wcf.IRegisterInfoService;
import pt.sibscartoes.portal.wcf.RegisterInfoService;

public class RegisterInfoWebServiceClient extends BennuWebServiceClient<IRegisterInfoService> {

    public IRegisterInfoService getRegisterService() {
        return getClient();
    }

    @Override
    protected BindingProvider getService() {
        return (BindingProvider) new RegisterInfoService().getRegisterInfoWsHttp();
    }

}
