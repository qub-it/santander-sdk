package org.fenixedu.santandersdk.service;

import javax.xml.ws.WebServiceException;

import org.datacontract.schemas._2004._07.sibscards_wcf_services.RegisterData;
import org.datacontract.schemas._2004._07.sibscards_wcf_services.TUIResponseData;
import org.datacontract.schemas._2004._07.sibscards_wcf_services.TuiPhotoRegisterData;
import org.datacontract.schemas._2004._07.sibscards_wcf_services.TuiSignatureRegisterData;
import org.fenixedu.santandersdk.dto.CardPreviewBean;
import org.fenixedu.santandersdk.dto.CreateRegisterRequest;
import org.fenixedu.santandersdk.dto.CreateRegisterResponse;
import org.fenixedu.santandersdk.dto.CreateRegisterResponse.ErrorType;
import org.fenixedu.santandersdk.dto.GetRegisterResponse;
import org.fenixedu.santandersdk.exception.SantanderValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.sibscartoes.portal.wcf.IRegisterInfoService;
import pt.sibscartoes.portal.wcf.ITUIDetailService;

public class SantanderSdkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SantanderSdkService.class);

    private SantanderLineGenerator santanderLineGenerator;

    public SantanderSdkService() {
        santanderLineGenerator = new SantanderLineGenerator(new SantanderEntryValidator());
    }

    public GetRegisterResponse getRegister(final String userName) {
        RegisterInfoWebServiceClient client = new RegisterInfoWebServiceClient();
        final IRegisterInfoService port = client.getRegisterService();
        final RegisterData registerData = port.getRegister(userName);
        return new GetRegisterResponse(registerData);
    }

    public CardPreviewBean generateCardRequest(final CreateRegisterRequest request) throws SantanderValidationException {
        return santanderLineGenerator.generateLine(request);
    }

    public CreateRegisterResponse createRegister(final CardPreviewBean cardPreviewBean) {
        final String tuiEntry = cardPreviewBean.getRequestLine();
        final TuiPhotoRegisterData photoRegisterData = createPhoto(cardPreviewBean.getPhoto());
        final TuiSignatureRegisterData signature = new TuiSignatureRegisterData();

        TuiWebserviceClient client = new TuiWebserviceClient();

        final ITUIDetailService port = client.getTuiDetailService();

        try {
            final TUIResponseData responseData = port.saveRegister(tuiEntry, photoRegisterData, signature);
            return new CreateRegisterResponse(responseData);
        } catch (final WebServiceException e) {
            LOGGER.error(String.format("An webservice error happened while trying to create santander register. -> %s",
                    e.getMessage()), e);
            return new CreateRegisterResponse(ErrorType.SANTANDER_COMMUNICATION, "santander communication error", e.getMessage());
        }
    }

    private TuiPhotoRegisterData createPhoto(final byte[] photoContents) {
        final String EXTENSION = ".jpeg";

        final TuiPhotoRegisterData photo = new TuiPhotoRegisterData();
        photo.setFileContents(photoContents);
        photo.setSize(Integer.toString(photoContents.length));
        photo.setExtension(EXTENSION);
        photo.setFileName("foto");

        return photo;
    }
}
