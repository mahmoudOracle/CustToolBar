package view.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
//import oracle.javatools.resourcebundle.BundleFactory;
import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.uicli.binding.JUCtrlListBinding;
import org.apache.myfaces.trinidad.context.RequestContext;

public class ToolBarUtils {
    /**
     * @param MethodName
     * @return
     */
    public String ExecuteMethod(String MethodName) { // Execute Method
        OperationBinding operationBinding = this.AccessOperation(MethodName);
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    /**
     * @param MyComponent
     * @param MyContext
     * @param Header
     * @param Footer
     * @param Level
     */
    public void ValidateItem(UIComponent MyComponent, FacesContext MyContext, String Header, String Footer, int Level) { // Force Validation
        FacesMessage MyMessage = new FacesMessage();
        if (Level == 1) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_FATAL, Header, Footer);
        } else if (Level == 2) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_ERROR, Header, Footer);
        } else if (Level == 3) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_WARN, Header, Footer);
        } else if (Level == 4) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_INFO, Header, Footer);
        }

        if (MyComponent instanceof RichInputText) {
            ((RichInputText) MyComponent).setValid(false);
        } else if (MyComponent instanceof RichSelectOneChoice) {
            ((RichSelectOneChoice) MyComponent).setValid(false);
        }

        MyContext.addMessage(MyComponent.getClientId(MyContext), MyMessage);

    }

    /**
     * @param MyContext
     * @param Header
     * @param Footer
     * @param Level
     */
    public void Validate(FacesContext MyContext, String Header, String Footer, int Level) { // Force Validation
        FacesMessage MyMessage = new FacesMessage();
        if (Level == 1) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_FATAL, Header, Footer);
        } else if (Level == 2) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_ERROR, Header, Footer);
        } else if (Level == 3) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_WARN, Header, Footer);
        } else if (Level == 4) {
            MyMessage = new FacesMessage(MyMessage.SEVERITY_INFO, Header, Footer);
        }

        MyContext.addMessage(null, MyMessage);

    }

    /*  public String AccessBundleItemValue(String BundleName, String BundleNameItem) {
        ResourceBundle bundle = BundleFactory.getBundle(BundleName);
        return bundle.getString(BundleNameItem);
    } */

    /**
     * @param Pop
     */
    public void ShowDialog(RichPopup Pop) { // Run Dialog using its Popup
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        Pop.show(hints);
    }

    /**
     * @param MyComponent
     * @param isValid
     */
    public void EnableDisableItem(UIComponent MyComponent, Boolean isValid) { // enable & Disable Items
        if (MyComponent instanceof RichInputText) {
            ((RichInputText) MyComponent).setDisabled(!isValid);
        } else if (MyComponent instanceof RichButton) {
            ((RichButton) MyComponent).setDisabled(!isValid);
        } else if (MyComponent instanceof RichButton) {
            ((RichButton) MyComponent).setDisabled(!isValid);
        } else if (MyComponent instanceof RichSelectBooleanCheckbox) {
            ((RichSelectBooleanCheckbox) MyComponent).setDisabled(!isValid);
        } else if (MyComponent instanceof RichInputListOfValues) {
            ((RichInputListOfValues) MyComponent).setDisabled(!isValid);
        }
    }

    /**
     * @param MyComponent
     * @param isVisable
     */
    public void VisableInvisableItem(UIComponent MyComponent,
                                     // Apearing the Items
                                     Boolean isVisable) {
        if (MyComponent instanceof RichInputText) {
            ((RichInputText) MyComponent).setVisible(isVisable);
        } else if (MyComponent instanceof RichButton) {
            ((RichButton) MyComponent).setVisible(isVisable);
        } else if (MyComponent instanceof RichButton) {
            ((RichButton) MyComponent).setVisible(isVisable);
        } else if (MyComponent instanceof RichSelectBooleanCheckbox) {
            ((RichSelectBooleanCheckbox) MyComponent).setVisible(isVisable);
        } else if (MyComponent instanceof RichInputListOfValues) {
            ((RichInputListOfValues) MyComponent).setVisible(isVisable);
        }
    }

    /**
     * @return
     */
    public BindingContainer getBindings() { // Access Data Control
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * @return
     */
    public FacesContext getFacesContext() { // Access Faces Context - JSF Resources
        return FacesContext.getCurrentInstance();
    }

    /**
     * @param AttributeName
     * @return
     */
    public AttributeBinding AccessAttribute(String AttributeName) { // Access AttributeBinding
        return (AttributeBinding) getBindings().get(AttributeName);
    }

    /**
     * @param IteratorName
     * @return
     */
    public DCIteratorBinding AccessIteratorBinding(String IteratorName) { // Access IteratorBinding
        return (DCIteratorBinding) getBindings().get(IteratorName);
    }

    /**
     * @param OperationName
     * @return
     */
    public OperationBinding AccessOperation(String OperationName) { // Access OperationBinding
        return getBindings().getOperationBinding(OperationName);
    }

    /**
     * @param MyOperation
     * @return
     */
    public Map GetParameters(String MyOperation) { // Access Map
        return getBindings().getOperationBinding(MyOperation).getParamsMap();
    }

    /**
     * @param IterBinding
     * @return
     */
    public ViewObject GetVO(String IterBinding) { // Access VO of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getViewObject();
    }

    /**
     * @param IterBinding
     * @return
     */
    public String GetWhereClause(String IterBinding) { // Access VO Where of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getViewObject().getWhereClause();
    }

    /**
     * @param IterBinding
     * @return
     */
    public Object[] GetWhereClauseParams(String IterBinding) { // Access VO Where Parameters of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getViewObject().getWhereClauseParams();
    }

    /**
     * @param IterBinding
     * @return
     */
    public Row GetCurrentRow(String IterBinding) { // Access Current Row of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getCurrentRow();
    }

    /**
     * @param IterBinding
     * @return
     */
    public ViewCriteria GetViewCriteria(String IterBinding) { // Access VC Row of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getViewCriteria();
    }

    /**
     * @param IterBinding
     * @return
     */
    public Long GetRowCount(String IterBinding) { // Access Estimated Row Count of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getEstimatedRowCount();
    }

    /**
     * @return
     */
    public Application GetAppModuleUsingFacesContext() { // Access AM of IteratorBinding
        return getFacesContext().getApplication();
    }

    /**
     * @param IterBinding
     * @return
     */
    public ApplicationModule GetAppModuleUsingIteratorBinding(String IterBinding) { // Access AM of IteratorBinding
        DCIteratorBinding MyIterator = (DCIteratorBinding) getBindings().get(IterBinding);
        return MyIterator.getDataControl().getApplicationModule();
    }

    /**
     * @param User
     * @param Password
     * @param IPAddress
     * @param Port
     * @param InstanceName
     * @return
     */
    public Boolean CheckUserNamePassword(String User, String Password, String IPAddress, String Port,
                                         String InstanceName) {
        Boolean FinalResult = false;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn =
                DriverManager.getConnection("jdbc:oracle:thin:@" + IPAddress + ":" + Port + ":" + InstanceName, User,
                                            Password);
        } catch (SQLException e) {
            FinalResult = false;
        }

        if (conn != null) {
            try {
                if (conn.isValid(20) == true) {
                    FinalResult = true;
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    FinalResult = false;
                }
            } catch (SQLException e) {
                FinalResult = false;
            }
        } else {
            FinalResult = false;
        }
        return FinalResult;
    }

    /**
     * @param MyBinding
     * @param AttName
     * @return
     */
    public Object AccessList(String MyBinding, String AttName) {
        JUCtrlListBinding MyList = (JUCtrlListBinding) AccessAttribute(MyBinding);
        ViewRowImpl MyRow = (ViewRowImpl) MyList.getSelectedValue();
        return MyRow.getAttribute(AttName);
    }


    /**
     * @param bindingListName
     * @param AttName
     * @return
     */
    public Object AccessLovList(String bindingListName, String AttName) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlListBinding list = (JUCtrlListBinding) bindings.get(bindingListName);
        return list.getAttribute(AttName);
    }

    /**
     * @param MyIteratorBinding
     * @return
     */
    public Boolean isDirty(String MyIteratorBinding) {
        ApplicationModule AM = GetAppModuleUsingIteratorBinding(MyIteratorBinding);
        return AM.getTransaction().isDirty();
    }

    /**
     * @param expr
     * @param returnType
     * @param argType
     * @param argument
     * @return
     */
    public static Object invokeMethodExpression(String expr, Class returnType, Class argType, Object argument) {
        return invokeMethodExpression(expr, returnType, new Class[] { argType }, new Object[] { argument });
    }

    /**
     * @param expr
     * @param returnType
     * @param argTypes
     * @param args
     * @return
     */
    public static Object invokeMethodExpression(String expr, Class returnType, Class[] argTypes, Object[] args) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elctx = fc.getELContext();
        ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
        MethodExpression methodExpr = elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
        return methodExpr.invoke(elctx, args);
    }


    /**
     * @param str
     * @param length
     * @param car
     * @return
     */
    public static String RPad(String str, Integer length, char car) {
        return str + String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car));
    }

    /**
     * @param str
     * @param length
     * @param car
     * @return
     */
    public static String LPad(String str, Integer length, char car) {
        return String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car)) + str;
    }

    /**
     * @param MyComponent
     */
    public void RefreshItem(UIComponent MyComponent) {
        RequestContext.getCurrentInstance().addPartialTarget(MyComponent);
    }

    /**
     * @param ParameterName
     * @param value
     * @param Typ
     */
    public void putScopeParameterValue(String ParameterName, Object value, String Typ) {
        if (Typ.equalsIgnoreCase("SessionScope")) {
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put(ParameterName, value);
        } else {
            ADFContext.getCurrent()
                      .getPageFlowScope()
                      .put(ParameterName, value);
        }
    }

    /**
     * @param ParameterName
     * @param Typ
     * @return
     */
    public Object getScopeParameterValue(String ParameterName, String Typ) {
        if (Typ.equalsIgnoreCase("SessionScope")) {
            return ADFContext.getCurrent()
                             .getSessionScope()
                             .get(ParameterName);
        } else {
            return ADFContext.getCurrent()
                             .getPageFlowScope()
                             .get(ParameterName);
        }
    }


    /**
     * @param _pLocale
     */
    public void SwitchLocale(String _pLocale) {
        if (_pLocale != null && !_pLocale.equals("")) {
            FacesContext fc = FacesContext.getCurrentInstance();
            Locale locale = new Locale(_pLocale);
            fc.getViewRoot().setLocale(locale);
        }
    } // Wael Abdeen

}
