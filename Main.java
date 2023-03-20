import java.io.*;
import java.util.*;

/**
 * Main class contain main method that start the program and manages menu system by calling appropriate methods.
 * The class contains methods that handle printing of menu options, reading file and creating unique signature.
 * In addition, It contains method for each menu selection.
 */
public class Main {
    /**
     * Create unique signature by using first 3 letters of both first and last name and serial number.
     * Signature form is xxxyyyzz. the function checks all signature in the list and increments serial number
     * if the created signature is not unique.
     * @param firstName firstname
     * @param lastName lastname
     * @param personArrayList person arraylist to check signature uniqueness
     * @return signature
     */
    public static String createUniqueSignature(String firstName,String lastName,ArrayList<Person> personArrayList){

        // replace white spaces from name with empty string ""
        firstName = firstName.replaceAll("[ ]+","");
        lastName = lastName.replaceAll("[ ]+","");
        // Substring first three chars if length >=3 otherwise take all chars
        StringBuilder signPart1 = new StringBuilder(firstName.substring(0, Math.min(firstName.length(), 3)));
        StringBuilder signPart2 = new StringBuilder(lastName.substring(0, Math.min(lastName.length(), 3)));
        // fill with x until length is 3
        signPart1.append("x".repeat(Math.max(0, 3 - signPart1.length())));
        signPart2.append("x".repeat(Math.max(0, 3 - signPart2.length())));
        // combine signPart1 and  signPart2 and convert to lowercase and assign it to string
        String signature = signPart1.append(signPart2).toString().toLowerCase();
        int serialNumber = 0;
        if(personArrayList !=null){
            // find all signature in list that contains the part xxxyyy and increment serialnumber for each
            for (Person person : personArrayList) {
                if (person.getSignature().contains(signature))
                    ++serialNumber;
            }
        }
        // last step is to increment serial number with 1 and combine it with signature to create unique one
        return String.format("%s%02d",signature, ++serialNumber);
    }

    /**
     * search for a person in arraylist by signature.
     * @param personArrayList arraylist of person type
     * @param signature person signature
     * @return index of Person object if founded or -1 if not founded
     */
    public static int findIndexOfPerson(ArrayList<Person> personArrayList, String signature){
        for (int index=0; index<personArrayList.size();index++){
            String PersonSignature=personArrayList.get(index).getSignature();
            if(signature.equalsIgnoreCase(PersonSignature))
                return index;
        }
        return -1;
    }

    /**
     * try read keyboard input unsigned int, on failure error message is printed on screen.
     * if the conversion fails it return -1.
     * if keyboard input not positive integer it returns -1
     * @return unsigned int when conversion success otherwise -1
     */
    public static int readKeyboardInputUnsignedInt(){
        int number=-1;
        Scanner scanner=new Scanner(System.in);
        String keyboardInput=scanner.nextLine();
        try {
            number= Integer.parseInt(keyboardInput);
            number=Math.max(number,-1);
        }
        catch (NumberFormatException exception){
            System.out.println("Error!!! only positive numbers are allowed....");
        }
        return number;
    }

    /**
     * return true if person object passed to the function is unique among the persons in the arraylist,
     * otherwise return false. The function checks first and last name and length to determine uniqueness.
     * @param personArrayList arraylist of person objects
     * @param person person object to be checked
     * @return true if two persons are same otherwise false
     */
    public static boolean isUniquePerson(ArrayList<Person> personArrayList,Person person){
        for (Person value : personArrayList) {
            boolean hasSameName = value.getFullName().equalsIgnoreCase(person.getFullName());
            boolean hasSameLength = (value.getLength() == person.getLength());
            if (hasSameName && hasSameLength)
                return false;
        }
        return true;
    }

    /**
     * reads and decrypt a file contains encrypted person objects.
     * if encryption key is wrong will produce errors.
     * @param filename filename
     * @param cipherKey key used to decrypt file contents
     * @return person arraylist
     */
    public static ArrayList<Person> readFile(String filename, int cipherKey){
        ArrayList<Person> personArrayList=new ArrayList<>();
        Encryption encryption = new Encryption(cipherKey);
        try {
            BufferedReader reader=new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null){
                String decryptLine=encryption.decrypt(line);
                String[] personComponents=decryptLine.split("\\|");
                Person person = new Person();
                Address address = new Address();
                try {
                    person.setFirstName(personComponents[0]);
                    person.setLastName(personComponents[1]);
                    person.setSignature(personComponents[2]);
                    person.setLength(Integer.parseInt(personComponents[3]));
                    address.setStreetName(personComponents[4]);
                    address.setZipCode(personComponents[5]);
                    address.setCity(personComponents[6]);
                    person.setAddress(address);
                    personArrayList.add(person);
                }catch (IndexOutOfBoundsException exception){
                    System.out.println("ERROR!!! Either the file is corrupt or decryption key is wrong");
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("ERROR!!!! either file not exists or it unreachable");
        }
        return personArrayList;
    }

    /**
     * Prints menu options
     */
    public static void printMenuOptions(){
        final String ADD="1-Add a person";
        final String PRINT="2-Print the list of people on the screen";
        final String SEARCH="3-Search for a person in the list";
        final String DELETE="4-Remove a person from the list";
        final String SORT_NAME="5-Sort the list by last name";
        final String SORT_SIG="6-Sort the list by signature";
        final String SORT_LEN="7-Sort the list by length";
        final String REORDER="8-Randomly reorder the order of the list";
        final String SAVE="9-Save the list in a text file";
        final String READ="10-Read the list from a text file";
        final String QUIT="11-Quit";
        final String FORMAT="\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n";
        System.out.printf(FORMAT,ADD,PRINT,SEARCH,DELETE,SORT_NAME,SORT_SIG,SORT_LEN,REORDER,SAVE,READ,QUIT);
    }

    /**
     * Ask user to enter person data, then controls if person is unique by calling isUniquePerson().
     * if person not unique 2 options are printed to choose, one to cancel and
     * one to change data. After entering all person data the person is added to list.
     * @param personArrayList person objects
     */
    public static void addPersonToList(ArrayList<Person> personArrayList){
        Scanner scanner=new Scanner(System.in);
        boolean isRunning;
        Person person = new Person();
        Address address = new Address();

        do{
            isRunning=false;
            System.out.print("Enter first name: ");
            person.setFirstName(scanner.nextLine());

            System.out.print("Enter last name: ");
            person.setLastName(scanner.nextLine());

            // length will be set to 0 if input number are negative
            System.out.print("Enter person length in cm: ");
            int personLength=readKeyboardInputUnsignedInt();
            person.setLength(personLength);
            if(personLength==-1)
                System.out.println("Oops! Length is set to default 0 cm");

            // control person uniqueness and provide options in case person is not unique
            if(!isUniquePerson(personArrayList,person)){
                System.out.println("Oops! There is already a person with same name and length in the list!!!!");
                int optionNum;
                // loop runs until the user enter valid option
                do {
                    System.out.println("1-Change person data");
                    System.out.println("2-Cancel the process");
                    System.out.print("Enter your option number: ");
                    optionNum=readKeyboardInputUnsignedInt();
                    if(optionNum==1)
                        isRunning=true;
                    if(optionNum==2)
                        return; // end the process
                }while (optionNum<1 || optionNum>2);
            }
        }while(isRunning);

        System.out.print("Enter Street name: ");
        address.setStreetName(scanner.nextLine());

        System.out.print("Enter ZIP code: ");
        address.setZipCode(scanner.nextLine());

        System.out.print("Enter city name: ");
        address.setCity(scanner.nextLine());

        person.setAddress(address);
        String signature= createUniqueSignature(person.getFirstName(),person.getLastName(),personArrayList);
        person.setSignature(signature);
        personArrayList.add(person);
        System.out.println("Done! person is added to list successfully");
    }

    /**
     * Print all person objects in personArrayList on screen.
     * @param personArrayList with person objects
     */
    public static void printPersons(ArrayList<Person> personArrayList){
        final String STRING_FORMAT="%-6s %-10s %-35s %-10s\n";
        System.out.printf(STRING_FORMAT,"Nr","Sign","Name","Length [m]");
        for(int index=0;index<personArrayList.size();index++){
            String fullName=personArrayList.get(index).getFullName();
            String signature=personArrayList.get(index).getSignature();
            // result it always 2 decimal places
            // dividing integer by 100 moves floating point to left by 2 places
            float lengthInMeters=(float) personArrayList.get(index).getLength()/100;
            System.out.printf(STRING_FORMAT,(index + 1 ),signature,fullName,lengthInMeters);
            // show 20 persons at a time
            if((index + 1)%20==0)
                readAnyKey();
        }
    }

    /**
     * it lets user search for a person by entering person signature as keyword.
     * If the searched person is in the list, the person is printed on the screen.
     * If person not found an error message is printed informing that the person is missing.
     * @param personArrayList person objects
     */
    public static void searchPersonInList(ArrayList<Person> personArrayList){
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter persons signature to search for: ");
        String keyboardInput= scanner.nextLine();
        int index=findIndexOfPerson(personArrayList, keyboardInput);
        if(index!=-1){
            ArrayList<Person> arrayList= new ArrayList<>(1);
            arrayList.add(personArrayList.get(index));
            printPersons(arrayList);
        }
        else
            System.out.printf("Sorry! we couldn't find any person with signature '%s'\n",keyboardInput);
    }

    /**
     * it lets user remove a person from list by entering person signature as keyword.
     * if person is found will be deleted from list and a message will be printed.
     * if person not found an error message is printed informing that the person is missing.
     * @param personArrayList person objects
     */
    public static void removePersonFromList(ArrayList<Person> personArrayList){
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter person signature to remove it from list: ");
        String keyboardInput= scanner.nextLine();
        int index=findIndexOfPerson(personArrayList, keyboardInput);
        if(index!=-1){
            personArrayList.remove(index);
            System.out.printf("Done! Person with signature '%s' is removed from list.\n",keyboardInput);
        }
        else
            System.out.printf("Sorry! we couldn't find any person with signature '%s'\n",keyboardInput);
    }

    /**
     * Sort list by person last name in ascending order a-z.
     * If the last names are the same, the first name is used as comparator.
     * After sorting a message will print on screen informing that the sort is completed.
     * @param personArrayList person objects
     */
    public static void sortByName(ArrayList<Person> personArrayList){
        personArrayList.sort(new PersonCompareName());
        System.out.println("Done! List are sorted by name now");
    }

    /**
     * Sort list by person signature in ascending order a-z.
     * After sorting a message will print on screen informing that the sort is completed.
     * @param personArrayList person objects
     */
    public static void sortBySignature(ArrayList<Person> personArrayList){
        personArrayList.sort(new PersonCompareSignature());
        System.out.println("Done! List are sorted by signature now");
    }

    /**
     * Sort list by person length from the biggest length to smallest.
     * After sorting a message will print on screen informing that the sort is completed.
     * @param personArrayList person objects
     */
    public static void sortByLength(ArrayList<Person> personArrayList){
        personArrayList.sort(new PersonCompareLength());
        System.out.println("Done! List are sorted by person-length now");
    }

    /**
     * randomly reorder the items in the list.
     * After reordering the list a message will print on screen informing that the process is completed.
     * @param personArrayList person objects
     */
    public static void randomListOrder(ArrayList<Person> personArrayList){
        Collections.shuffle(personArrayList,new Random(System.currentTimeMillis()));
        System.out.println("Done! list items are now randomly reordered");
    }

    /**
     * Asks user for file name to save list into file and encryption key to encrypt list items
     * before writing it to file. If file not exists an error is printed on screen.
     * @param personArrayList person objects
     */
    public static void saveListToFile(ArrayList<Person> personArrayList){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Note!! The file you provide will be overwritten, Use -1 as filename to cancel");
        System.out.print("Enter file name to write list into: ");
        String filename=scanner.nextLine();
        if(filename.equals("-1")) // cancel writing file if -1 used as file name
            return;
        System.out.print("Enter encryption key: ");
        int cipherKey=readKeyboardInputUnsignedInt();
        // when cipher key incorrect print message informing the key set to 0
        if(cipherKey <0)
            System.out.println("Oops! key is set to default 0");

        Encryption encryption=new Encryption(cipherKey);
        try {
            File file=new File(filename);
            if(!file.exists()){
                System.out.println("ERROR!! File not found");
                return;
            }
            // Clear file contents before writing into the file
            new FileWriter(filename, false).close();
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(filename,true));
            for (Person person : personArrayList) {
                bufferedWriter.write(encryption.encrypt(person.toString()));
                bufferedWriter.newLine();
            }
            System.out.printf("Done! There were %s items written to file '%s' \n",personArrayList.size(),filename);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR with the file you provided!!");
        }
    }

    /**
     * it clears person list and asks user to enter file name and decryption key,
     * then it calls readFile() and assign file contents to personArrayList.
     * if the file is corrupt or encryption key not matching an error is printed on screen.
     * @param personArrayList arraylist of type person
     */
    public static void readListFromFile(ArrayList<Person> personArrayList){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Note!! The list will be cleared after entering filename, Use -1 as filename to cancel");
        System.out.print("Enter file name to read list from: ");
        String filename=scanner.nextLine();
        if(filename.equals("-1")) // cancel reading file if -1 used as file name
            return;
        personArrayList.clear();
        System.out.print("Enter decryption key: ");
        int cipherKey=readKeyboardInputUnsignedInt();
        // when cipher key incorrect print message informing about it
        if(cipherKey <0)
            System.out.println("Oops! Key is set to default 0, This may produce errors if file has different key.");

        ArrayList<Person> persons=readFile(filename,cipherKey);
        if(persons.isEmpty())
            return;
        personArrayList.addAll(persons);
        System.out.println("The process is done!");
    }

    /**
     * Print a message telling user to press any keyboard key to continue.
     * it reads keyboard input but do nothing.
     */
    public static void readAnyKey(){
        System.out.print("\nClick any keyboard key to continue.....");
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();
        scanner.reset();
        System.out.println();
    }

    // main program
    public static void main(String[] args) {
        final String LIST_NAME="****** People List ******";
        final String DIVIDER ="*".repeat(50);
        ArrayList<Person> personArrayList = new ArrayList<>();
        boolean isRunning=true;
        do {
            System.out.printf("%s\n%s\nNumber of persons in list: %d\n%s",DIVIDER,LIST_NAME,personArrayList.size(),DIVIDER);
            printMenuOptions();
            System.out.printf("%s\nEnter your option number: ",DIVIDER);
            int optionNum =readKeyboardInputUnsignedInt();

            switch (optionNum) {
                case 1 -> addPersonToList(personArrayList);
                case 2 -> printPersons(personArrayList);
                case 3 -> searchPersonInList(personArrayList);
                case 4 -> removePersonFromList(personArrayList);
                case 5 -> sortByName(personArrayList);
                case 6 -> sortBySignature(personArrayList);
                case 7 -> sortByLength(personArrayList);
                case 8 -> randomListOrder(personArrayList);
                case 9 -> saveListToFile(personArrayList);
                case 10 -> readListFromFile(personArrayList);
                case 11 -> {
                    isRunning = false;
                    System.out.println("Bye!");
                }
                default -> System.out.println("Wrong option number!! Choose options 1-11 only from menu");
            }
            if(optionNum != 11)
                readAnyKey();
        }while (isRunning);
    }
}
