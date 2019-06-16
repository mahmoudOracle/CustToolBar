package view.beans;

import oracle.jbo.DMLException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
public class Dml {
    ToolBarUtils mybean = new ToolBarUtils();
    private String MethodNameorIteratorBinding = "";
    private String InsertType = "";

    /**
     * @return
     */
    public String CreateInsert() {
        if (getMethodNameorIteratorBinding() != null) {
            RowSetIterator Schedual =
                mybean.AccessIteratorBinding(getMethodNameorIteratorBinding()).getRowSetIterator();
            Row lastRow, newRow = Schedual.createRow();
            if (getInsertType().equalsIgnoreCase("last")) {
                lastRow = Schedual.last();
            } else {
                lastRow = Schedual.getCurrentRow();
            }
            int lastRowIndex = Schedual.getRangeIndexOf(lastRow);
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
            Schedual.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
            Schedual.setCurrentRow(newRow);

        }
        return null;
    }

    /**
     * @return
     */
    public String Delete() {
        if (getMethodNameorIteratorBinding() != null) {
            (mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())).getRowSetIterator().removeCurrentRow();
        }
        return null;
    }

    /**
     * @return
     */
    public String Next() {
        if (getMethodNameorIteratorBinding() != null) {
            (mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())).getRowSetIterator().next();
        }
        return null;
    }

    /**
     * @return
     */
    public String Previous() {
        if (getMethodNameorIteratorBinding() != null) {
            (mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())).getRowSetIterator().previous();
        }
        return null;
    }

    /**
     * @return
     */
    public String First() {
        if (getMethodNameorIteratorBinding() != null) {
            (mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())).getRowSetIterator().first();
        }
        return null;
    }

    /**
     * @return
     */
    public String Last() {
        if (getMethodNameorIteratorBinding() != null) {
            (mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())).getRowSetIterator().last();
        }
        return null;
    }

    /**
     * @return
     */
    public String Commit() {
        if (getMethodNameorIteratorBinding() != null) {
            try {
                mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())
                      .getDataControl()
                      .commitTransaction();
                mybean.Validate(mybean.getFacesContext(), "Commit ..", "Data has been Successfully Saved !", 4);
            } catch (DMLException e) {
                mybean.Validate(mybean.getFacesContext(), "Commit ..", e.getErrorCode() + " - " + e.getBaseMessage(),
                                1);
            }
        }
        return null;
    }

    /**
     * @return
     */
    public String Rollback() {
        if (getMethodNameorIteratorBinding() != null) {
            try {
                mybean.AccessIteratorBinding(getMethodNameorIteratorBinding())
                      .getDataControl()
                      .rollbackTransaction();
                mybean.Validate(mybean.getFacesContext(), "RollBack ..", "Data has been Reverted Back !", 4);
            } catch (DMLException e) {
                mybean.Validate(mybean.getFacesContext(), "RollBack ..", e.getErrorCode() + " - " + e.getBaseMessage(),
                                1);
            }
        }
        return null;
    }

    /**
     * @return
     */
    public String information() {
        mybean.Validate(mybean.getFacesContext(), "STB ..", "STB | Smart ToolBar has been Developed by 'Wael Abdeen'.",
                        4);
        return null;
    }

    /**
     * @param MethodNameorIteratorBinding
     */
    public void setMethodNameorIteratorBinding(String MethodNameorIteratorBinding) {
        this.MethodNameorIteratorBinding = MethodNameorIteratorBinding;
    }

    /**
     * @return
     */
    public String getMethodNameorIteratorBinding() {
        return MethodNameorIteratorBinding;
    }


    /**
     * @param InsertType
     */
    public void setInsertType(String InsertType) {
        this.InsertType = InsertType;
    }

    /**
     * @return
     */
    public String getInsertType() {
        return InsertType;
    }
    }


