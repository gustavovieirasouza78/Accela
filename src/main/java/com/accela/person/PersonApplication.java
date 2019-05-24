package com.accela.person;

import com.accela.person.entities.Person;
import com.accela.person.services.PersonService;
import com.accela.person.util.ReadXMLFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class PersonApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class, args);
    }

    @Autowired
    PersonService personService;

    private final String[] commands = {"add", "edit", "del", "count", "list", "help", "exit"};

    @Autowired
    Environment environment;

    @Override
    public void run(String... args) throws Exception {

        String[] profiles = environment.getActiveProfiles();
        String profile = "dev";
        if(profiles!=null && profiles.length>0)
            profile = profiles[0];

        if(!profile.equals("test")){

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("$>");

                String readLine = scanner.nextLine();
                int command = getCommand(readLine);

                switch (command) {
                    case 1:
                        addPerson(readLine);
                        break;
                    case 2:
                        editPerson(readLine);
                        break;
                    case 3:
                        deletePerson(readLine);
                        break;
                    case 4:
                        countPerson();
                        break;
                    case 5:
                        listPerson();
                        break;
                    case 6:
                        help();
                        break;
                    case 7:
                        exit();
                        break;
                    default:
                        help();
                        break;
                }
            }
        }
    }

    private int getCommand(String readLine){
        for(int i = 0; i<commands.length; i++){
            if(readLine.startsWith(commands[i]))
                return ++i;
        }
        return -1;
    }


    private void addPerson(String readLine){
        String regexAdd = "add\\s\\d+\\s\\w+\\s\\w+";
        String regesAddFile = "add\\s.+.xml";
        if(commandValidate(readLine, regexAdd)) {
            Person person = getPerson(readLine);
            Optional<Person> personAdd = this.personService.add(person);
            if (personAdd.isPresent()) {
                System.out.println("Added Person: " + personAdd.get().toString());
            } else {
                System.out.println("Person not added. Already exists a person with this id.");
            }
        }else if(commandValidate(readLine, regesAddFile)) {
            StringTokenizer tokenizer = new StringTokenizer(readLine, " ");
            tokenizer.nextToken();
            String filePath = tokenizer.nextToken();
            List<Person> personList = ReadXMLFile.parseXML(filePath);

            for(Person person : personList){
                Optional<Person> personAdd = this.personService.add(person);
                if (personAdd.isPresent()) {
                    System.out.println("Added Person: " + personAdd.get().toString());
                }
            }

        }else{
            System.out.println("Invalid Command! \n");
            addCommandHelp();
        }
    }

    private void addCommandHelp(){
        System.out.println(
                "add <id> <name> <surname>"+"\n"+
                "   ie.: add 1 Theresa May"+"\n"+
                "add <file_path>"+"\n"+
                "   ie.: add c:\\archive.xml"+"\n"+
                "      <?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                        "         <listPerson>\n" +
                        "             <person>\n" +
                        "                 <id>1</id>\n" +
                        "                 <name>Theresa</name>\n" +
                        "                 <surname>May</surname>\n" +
                        "             </person>\n" +
                        "             <person>\n" +
                        "                 <id>2</id>\n" +
                        "                 <name>Bill</name>\n" +
                        "                 <surname>Clinton</surname>\n" +
                        "             </person>\n" +
                        "         </listPerson>\n"
        );
    }

    private void editPerson(String readLine){
        String regexEdit = "edit\\s\\d+\\s\\w+\\s\\w+";
        if(commandValidate(readLine, regexEdit)) {
            Person person = getPerson(readLine);
            Optional<Person> personEdited = this.personService.edit(person);
            System.out.println("Edited Person: " + personEdited.get().toString());
        }else{
            System.out.println("Invalid Command! \n");
            editCommandHelp();
        }
    }

    private void editCommandHelp(){
        System.out.println(
                "edit <id> <name> <surname>"+"\n"+
                        "   ie.: edit 1 Theresa May"+"\n"
        );
    }

    private void deletePerson(String readLine){
        String regexDelete = "(del|delete)\\s\\d+";
        if(commandValidate(readLine, regexDelete)) {
            StringTokenizer tokenizer = new StringTokenizer(readLine, " ");
            tokenizer.nextToken();
            Long id = Long.parseLong(tokenizer.nextToken());
            if(this.personService.delete(id)){
                System.out.println("Deleted id: " + id);
            }else{
                System.out.println("Not Deleted. Id do not exist.");
            }
        }else{
            System.out.println("Invalid Command! \n");
            deleteCommandHelp();
        }
    }

    private void deleteCommandHelp(){
        System.out.println(
                "del|delete <id>"+"\n"+
                        "   ie.: delete 1"+"\n"
        );
    }

    private void countPerson(){
        System.out.println("Number of people: " + this.personService.count());
    }

    private void countCommandHelp(){
        System.out.println(
                "count"+"\n"
        );
    }

    private void listPerson(){
        Optional<List<Person>> people = this.personService.list();
        if(people.isPresent()){
            System.out.println("List of People");
            people.get().forEach(person -> System.out.println(person.toString()));
        }
    }

    private void listCommandHelp(){
        System.out.println("count"+"\n");
    }

    private void help(){
        System.out.println("Help! See the command below: \n");
        addCommandHelp();
        editCommandHelp();
        deleteCommandHelp();
        countCommandHelp();
        System.out.println("exit"+"\n");
    }

    private void exit(){
        System.out.println("Logout");
        System.exit(0);
    }

    private boolean commandValidate(String readLine, String regex){
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(readLine);
        return matcher.matches();
    }

    private Person getPerson(String readLine){
        Person person = new Person();
        StringTokenizer tokenizer = new StringTokenizer(readLine, " ");
        tokenizer.nextToken();
        person.setId(Long.parseLong(tokenizer.nextToken()));
        person.setName(tokenizer.nextToken());
        person.setSurname(tokenizer.nextToken());

        return person;
    }
}
