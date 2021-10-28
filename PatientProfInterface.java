package prog;

import gui.ConfirmedGUI;
import gui.ErrorGUI;
public class PatientProfInterface
{

    private String file;
    private PatientProfDB db;

    //init interface with clean db every time
    public PatientProfInterface(String file)
    {
        this.file = file;
        this.db = new PatientProfDB(file);
    }

    //gets db variable from this
    public PatientProfDB getDB()
    {
        return this.db;
    }

    //deletes a patient profile, returns status
    public boolean deletePatientProf(String adminid, String last)
    {
        String patient = last;
        String id = adminid;

    
        if(this.db.deleteProfile(id, patient))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    //finds patient profile specified
    public PatientProf findPatientProf(String adminid, String lname)
    {
        String patient = lname;

        String id = adminid;

        PatientProf dude = this.db.findProfile(id, patient);
        if(dude == null)
        {
            return null;
        }
        else
        {
            return dude;
        }

        //in.close();
    }

    //allows user to pick attributes to update a patient profile
    public void updatePatientProf(String adminid, String last, String choice, String value)
    {
        String patient = last;

        String id = adminid;

        PatientProf dude = this.db.findProfile(id, patient);

        if(dude == null)
        {
            ErrorGUI bad = new ErrorGUI();
            return;
            //in.close();
        }

        System.out.println("Patient found. Please choose an option below:");
        
        switch(choice)
        {
            case "Address":
                dude.updateAddress(value);
                break;
            case "Phone":
                dude.updatePhone(value);
                break;
            case "Insurance Type":
                dude.updateInsuType(value);
                break;
            case "Copay":
                dude.updateCopay(Integer.parseInt(value));
                break;
            case "Patient Type":
                dude.updatePatientType(value);
                break;
        }

        ConfirmedGUI confm = new ConfirmedGUI();
        //in.close();
        
    }


    //saves database
    public void writeToDB()
    {
        System.out.println("Saving to database file: " + this.file);
        this.db.writeAllPatientProf();
    }

    //loads database
    public void initDB()
    {
        System.out.println("Loading database file: " + this.file);
        this.db.initalizeDatabase();
    }

    //prompts user for all info needed to make a patient profile, and adds it to memory.
    public void createNewPatientProf(String admin, String first, String last, String address, String phone, String cpay, String insurance, String patType, String mdcon, String mdph, String allerg, String ill)
    {

        System.out.print("adminID: ");
        String adminid = admin;

        System.out.print("First Name: ");
        String fname = first;

        System.out.print("Last Name: ");
        String lname = last;

        System.out.print("Address: ");
        String addr = address;

        System.out.print("Phone: ");
        String phn = phone;

        System.out.print("Copay: ");
        int pay = Integer.parseInt(cpay);

        System.out.print("Insu Type: ");
        String insu = insurance;

        System.out.print("Patient Type: ");
        String type = patType;

        MedCond meds = this.createNewMedCond(mdcon, mdph, allerg, ill);

        PatientProf newPatient = new PatientProf(adminid, fname, lname, addr, phn, pay, insu, type, meds);

        this.db.insertNewProfile(newPatient);

        System.out.println("Patient Added!");

    }

    //asks user for all info needed to make a new med condition, adds it to a patient profile.
    public MedCond createNewMedCond(String mdcon, String mdphn, String allerg, String illness)
    {
        System.out.println("Please enter patient Medical condition information");
    
        System.out.print("MD Contact: ");
        String contact = mdcon;
        
        System.out.print("MD Phone: ");
        String mdphone = mdphn;

        System.out.print("Allergy Type: ");
        String alg = allerg;

        System.out.print("Illness Type: ");
        String ill = illness;

        return new MedCond(contact, mdphone, alg, ill);
    }
}
