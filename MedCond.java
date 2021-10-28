package prog;
public class MedCond 
{
    private String mdContact;
    private String mdPhone;
    private String algType;
    private String illType;

    //constructor with no arguments
    public MedCond()
    {
        this.mdContact = "default";
        this.mdPhone = "default";
        this.algType = "default";
        this.illType = "default";
    }

    //constructor with all arguments
    public MedCond(String mdContact, String mdPhone, String algType, String illType)
    {
        this.mdContact = mdContact;
        this.mdPhone = mdPhone;
        this.algType = algType;
        this.illType = illType;
    }

    //==============================
    //get and set methods below here
    //==============================

    public String getMdContact() {
        return this.mdContact;
    }

    public void updateMdContact(String mdContact) {
        this.mdContact = mdContact;
    }

    public String getMdPhone() {
        return this.mdPhone;
    }

    public void updateMdPhone(String mdPhone) {
        this.mdPhone = mdPhone;
    }

    public String getAlgType() {
        return this.algType;
    }

    public void updateAlgType(String algType) {
        this.algType = algType;
    }

    public String getIllType() {
        return this.illType;
    }

    public void updateIllType(String illType) {
        this.illType = illType;
    }

}
