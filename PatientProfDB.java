package prog;
import java.util.Scanner;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class PatientProfDB
{
    private int numPatient = 0;
    private int currentPatientIndex = 0;
    private String fileName;
    private PatientProf[] patientList;

    //init patientProfDB
    public PatientProfDB(String dbname)
    {
        this.fileName = dbname;
        this.patientList = new PatientProf[0];
    }

    //append a new profile at the end of the patientList
    public void insertNewProfile(PatientProf profile)
    {
        this.patientList = Arrays.copyOf(this.patientList, this.numPatient + 1);
        this.numPatient++;
        this.patientList[this.numPatient - 1] = profile;
    }

    //method to search patient list for specific adminID and lastName. If present, return index of profile. If missing, returns -1.
    public int profileIndex(String id, String lname)
    {
        int found = -1;

        for(int i = 0; i < this.numPatient; i++)
        {
            if(this.patientList[i].getAdminID().equals(id) && this.patientList[i].getLastName().equals(lname))
            {
                found = i;
                break;
            }
        }

        return found;
    }

    //looks for a profile. if present, returns the profile. if not, returns a empty one...?
    public PatientProf findProfile(String id, String lname)
    {
        int index = this.profileIndex(id, lname);
        if(index != -1)
        {
            return this.patientList[index];    
        }
        else
        { //didnt find anything
            //PatientProf temp = new PatientProf();
            //return temp;
            return null;
        }
    }

    //deletes a profile if present. Does not erase the last entry of patientlist entirely, but we decrement the numPatients so it appears we delete it.
    public boolean deleteProfile(String id, String lname)
    {
        int index = this.profileIndex(id, lname);
        if(index == -1) return false;
        else
        {
            for(int i = index; i < this.numPatient - 1; i++)
            {
                this.patientList[i] = this.patientList[i + 1];
            }

            this.patientList[this.numPatient - 1] = null;
            this.numPatient--;
            return true;
        }
    }

    //finds first instance of a profile with adminID id
    public PatientProf findFirstProfile(String id)
    {
        this.currentPatientIndex = 0;
        for(int i = 0; i < this.numPatient; i++)
        {
            if(this.patientList[i].getAdminID().equals(id))
            {
                this.currentPatientIndex = i;
                return this.patientList[i];
            }
        }

        //if nothing found
        return null;
    }

    //finds next instance of a profile with adminID id, assuming we called findFirstProfile already
    public PatientProf findNextProfile(String id)
    {
        for(int i = this.currentPatientIndex + 1; i < this.numPatient; i++)
        {
            if(this.patientList[i].getAdminID().equals(id))
            {
                this.currentPatientIndex = i;
                return this.patientList[i];
            }
        }

        //if nothing found
        return null;
    }

    //writes all profiles in memory to file specified at fileName attribute
    public void writeAllPatientProf()
    {
        try
        {
            File file = new File(this.fileName);
            if(!file.exists()) file.createNewFile();
            FileWriter writer = new FileWriter(this.fileName);

            for(int i = 0; i < this.numPatient; i++)
            {
                String content = "";
                content += this.patientList[i].getAdminID() + "\n";
                content += this.patientList[i].getFirstName() + "\n";
                content += this.patientList[i].getLastName() + "\n";
                content += this.patientList[i].getAddress() + "\n";
                content += this.patientList[i].getPhone() + "\n";
                content += this.patientList[i].getCopay() + "\n";
                content += this.patientList[i].getInsuType() + "\n";
                content += this.patientList[i].getPatientType() + "\n";
                content += this.patientList[i].getMedCondInfo().getMdContact() + "\n";
                content += this.patientList[i].getMedCondInfo().getMdPhone() + "\n";
                content += this.patientList[i].getMedCondInfo().getAlgType() + "\n";
                content += this.patientList[i].getMedCondInfo().getIllType() + "\n";

                writer.write(content);
            }

            writer.close();
            System.out.println("Successfully wrote to file: " + this.fileName);

        }
        catch(IOException e)
        {
            System.out.println("Oops, something bad happened");
            e.printStackTrace();
        }
    }

    //loads database from textfile to memory. Ignores incomplete profiles.
    public void initalizeDatabase()
    {
        try
        {
            int patientAttribute = 0;
            String[] attributes = new String[12];

            this.patientList = new PatientProf[0];
            this.numPatient = 0;
            this.currentPatientIndex = 0;

            File file = new File(this.fileName);
            Scanner fileReader = new Scanner(file);

            while(true)
            {
                attributes[patientAttribute] = fileReader.nextLine();
                patientAttribute++;

                if(patientAttribute == 12)
                {
                    MedCond med = new MedCond(attributes[8], attributes[9], attributes[10], attributes[11]);
                    PatientProf patient = new PatientProf(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], Float.parseFloat(attributes[5]), attributes[6], attributes[7], med);
                    this.insertNewProfile(patient);
                    patientAttribute = 0;
                }
            
                if(!fileReader.hasNextLine()) break;
            }

            fileReader.close();
            System.out.println("Database loaded from file: " + this.fileName);
        }

        catch(NoSuchElementException e)
        {
            System.out.println("DB is empty, nothing to initalize");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Oops, file not found");
            e.printStackTrace();
        }
    }

}