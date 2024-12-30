
package alom.services;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the alom.services package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _JoinChannel_QNAME = new QName("http://services.alom/", "joinChannel");
    private static final QName _JoinChannelResponse_QNAME = new QName("http://services.alom/", "joinChannelResponse");
    private static final QName _LeaveChannel_QNAME = new QName("http://services.alom/", "leaveChannel");
    private static final QName _LeaveChannelResponse_QNAME = new QName("http://services.alom/", "leaveChannelResponse");
    private static final QName _Login_QNAME = new QName("http://services.alom/", "login");
    private static final QName _LoginResponse_QNAME = new QName("http://services.alom/", "loginResponse");
    private static final QName _Register_QNAME = new QName("http://services.alom/", "register");
    private static final QName _RegisterResponse_QNAME = new QName("http://services.alom/", "registerResponse");
    private static final QName _SendChannelMessage_QNAME = new QName("http://services.alom/", "sendChannelMessage");
    private static final QName _SendChannelMessageResponse_QNAME = new QName("http://services.alom/", "sendChannelMessageResponse");
    private static final QName _SendPrivateMessage_QNAME = new QName("http://services.alom/", "sendPrivateMessage");
    private static final QName _SendPrivateMessageResponse_QNAME = new QName("http://services.alom/", "sendPrivateMessageResponse");
    private static final QName _ValidateToken_QNAME = new QName("http://services.alom/", "validateToken");
    private static final QName _ValidateTokenResponse_QNAME = new QName("http://services.alom/", "validateTokenResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: alom.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JoinChannel }
     * 
     * @return
     *     the new instance of {@link JoinChannel }
     */
    public JoinChannel createJoinChannel() {
        return new JoinChannel();
    }

    /**
     * Create an instance of {@link JoinChannelResponse }
     * 
     * @return
     *     the new instance of {@link JoinChannelResponse }
     */
    public JoinChannelResponse createJoinChannelResponse() {
        return new JoinChannelResponse();
    }

    /**
     * Create an instance of {@link LeaveChannel }
     * 
     * @return
     *     the new instance of {@link LeaveChannel }
     */
    public LeaveChannel createLeaveChannel() {
        return new LeaveChannel();
    }

    /**
     * Create an instance of {@link LeaveChannelResponse }
     * 
     * @return
     *     the new instance of {@link LeaveChannelResponse }
     */
    public LeaveChannelResponse createLeaveChannelResponse() {
        return new LeaveChannelResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     * @return
     *     the new instance of {@link Login }
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     * @return
     *     the new instance of {@link LoginResponse }
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link Register }
     * 
     * @return
     *     the new instance of {@link Register }
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     * @return
     *     the new instance of {@link RegisterResponse }
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link SendChannelMessage }
     * 
     * @return
     *     the new instance of {@link SendChannelMessage }
     */
    public SendChannelMessage createSendChannelMessage() {
        return new SendChannelMessage();
    }

    /**
     * Create an instance of {@link SendChannelMessageResponse }
     * 
     * @return
     *     the new instance of {@link SendChannelMessageResponse }
     */
    public SendChannelMessageResponse createSendChannelMessageResponse() {
        return new SendChannelMessageResponse();
    }

    /**
     * Create an instance of {@link SendPrivateMessage }
     * 
     * @return
     *     the new instance of {@link SendPrivateMessage }
     */
    public SendPrivateMessage createSendPrivateMessage() {
        return new SendPrivateMessage();
    }

    /**
     * Create an instance of {@link SendPrivateMessageResponse }
     * 
     * @return
     *     the new instance of {@link SendPrivateMessageResponse }
     */
    public SendPrivateMessageResponse createSendPrivateMessageResponse() {
        return new SendPrivateMessageResponse();
    }

    /**
     * Create an instance of {@link ValidateToken }
     * 
     * @return
     *     the new instance of {@link ValidateToken }
     */
    public ValidateToken createValidateToken() {
        return new ValidateToken();
    }

    /**
     * Create an instance of {@link ValidateTokenResponse }
     * 
     * @return
     *     the new instance of {@link ValidateTokenResponse }
     */
    public ValidateTokenResponse createValidateTokenResponse() {
        return new ValidateTokenResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JoinChannel }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link JoinChannel }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "joinChannel")
    public JAXBElement<JoinChannel> createJoinChannel(JoinChannel value) {
        return new JAXBElement<>(_JoinChannel_QNAME, JoinChannel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JoinChannelResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link JoinChannelResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "joinChannelResponse")
    public JAXBElement<JoinChannelResponse> createJoinChannelResponse(JoinChannelResponse value) {
        return new JAXBElement<>(_JoinChannelResponse_QNAME, JoinChannelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LeaveChannel }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LeaveChannel }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "leaveChannel")
    public JAXBElement<LeaveChannel> createLeaveChannel(LeaveChannel value) {
        return new JAXBElement<>(_LeaveChannel_QNAME, LeaveChannel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LeaveChannelResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LeaveChannelResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "leaveChannelResponse")
    public JAXBElement<LeaveChannelResponse> createLeaveChannelResponse(LeaveChannelResponse value) {
        return new JAXBElement<>(_LeaveChannelResponse_QNAME, LeaveChannelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Login }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Register }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendChannelMessage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SendChannelMessage }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "sendChannelMessage")
    public JAXBElement<SendChannelMessage> createSendChannelMessage(SendChannelMessage value) {
        return new JAXBElement<>(_SendChannelMessage_QNAME, SendChannelMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendChannelMessageResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SendChannelMessageResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "sendChannelMessageResponse")
    public JAXBElement<SendChannelMessageResponse> createSendChannelMessageResponse(SendChannelMessageResponse value) {
        return new JAXBElement<>(_SendChannelMessageResponse_QNAME, SendChannelMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendPrivateMessage }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SendPrivateMessage }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "sendPrivateMessage")
    public JAXBElement<SendPrivateMessage> createSendPrivateMessage(SendPrivateMessage value) {
        return new JAXBElement<>(_SendPrivateMessage_QNAME, SendPrivateMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendPrivateMessageResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SendPrivateMessageResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "sendPrivateMessageResponse")
    public JAXBElement<SendPrivateMessageResponse> createSendPrivateMessageResponse(SendPrivateMessageResponse value) {
        return new JAXBElement<>(_SendPrivateMessageResponse_QNAME, SendPrivateMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateToken }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateToken }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "validateToken")
    public JAXBElement<ValidateToken> createValidateToken(ValidateToken value) {
        return new JAXBElement<>(_ValidateToken_QNAME, ValidateToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateTokenResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateTokenResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.alom/", name = "validateTokenResponse")
    public JAXBElement<ValidateTokenResponse> createValidateTokenResponse(ValidateTokenResponse value) {
        return new JAXBElement<>(_ValidateTokenResponse_QNAME, ValidateTokenResponse.class, null, value);
    }

}
