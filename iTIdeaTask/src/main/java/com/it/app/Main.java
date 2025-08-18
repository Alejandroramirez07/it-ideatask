package com.it.app;

import com.it.compInterfaces.StockProjects;
import com.it.company.*;
import com.it.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

import static com.it.company.LawRequirements.lawsIncluded;
import static com.it.company.Team.maxTeam;
import static com.it.company.LawRequirements.getDaysUntilApprobation;


public class Main {
    public static void main(String[] args) {
        final Logger logger = (Logger) LogManager.getLogger(Main.class);

        Scanner scanner = new Scanner(System.in);

        logger.debug("User prompted to enter project information ");
        logger.info("Enter client name");
        String clientName = scanner.nextLine();
        boolean isValidClient = Client.isValidClientName(clientName);

        while (!isValidClient) {
            logger.info("Your last Project name " + clientName +  " is too short, Enter project name with minimum length of 3: ");
            clientName = scanner.nextLine();
            isValidClient = Client.isValidClientName(clientName);
        }

        logger.info("Describe the project idea: ");
        String ideaDescription = scanner.nextLine();
        boolean isDescriptionValid = Client.isValidIdeaDescription(ideaDescription);

        while (!isDescriptionValid) {
            logger.error("WRONG ANSWER");
            logger.info("Your last Project description " + ideaDescription +  " is too short, Enter project name with minimum length of 11: ");
            ideaDescription = scanner.nextLine();
            isDescriptionValid = Client.isValidIdeaDescription(ideaDescription);
        }

        logger.info("Is the project National or International (nat/inter) ");
        String scope = scanner.nextLine().toLowerCase(Locale.ROOT);

        while (!Objects.equals(scope, "nat") && !Objects.equals(scope, "inter")){
            logger.error("WRONG ANSWER");
            logger.info("Please, write the scope in terms of nat or inter");
            scope = scanner.nextLine();
        }

        Client client = new Client(clientName, ideaDescription, scope);

        logger.info("Enter project name: ");
        String projectName = scanner.nextLine();
        boolean isNameValid = Project.isValidProjectName(projectName);

        while (!isNameValid) {
            logger.error("WRONG ANSWER");
            logger.info("Your last Project name " + projectName +  " is too short, Enter project name with minimum length of 4: ");
            projectName = scanner.nextLine();
            isNameValid = Project.isValidProjectName(projectName);
        }

        logger.info("Enter project complexity (low / mid / high): ");
        String complexity = scanner.nextLine();

        while (!Objects.equals(complexity, "low") && !Objects.equals(complexity, "mid") && !Objects.equals(complexity, "high")){
            logger.error("WRONG ANSWER");
            logger.info("Your entry failed. Choose complexity level low/mid/high (write one of the three options shown before without changes): ");
            complexity = scanner.nextLine();
        }

        int features=0;

        while(true) {
            try {
                logger.info("Enter estimated number of features: ");
                features = scanner.nextInt();
                scanner.nextLine();
                break;
            }catch (InputMismatchException e){
                logger.info("input must be an Integer " + e.getMessage() + " Please, try again");
                scanner.nextLine();
            }
        }

        logger.info("Enter initial budget: ");
        float budget = scanner.nextFloat();
        scanner.nextLine();

        logger.info("Enter the deadline date for this project MM/DD/YYYY");
        String deadlineDelivery = scanner.nextLine();

        Project project = new Project(projectName, complexity, budget, clientName);

        List<Developer> devList = new ArrayList<>();

        logger.info("Enter number of developers: ");
        int numDevs = scanner.nextInt();
        scanner.nextLine();

        while (numDevs>maxTeam){
            logger.error("WRONG ANSWER");
            logger.info("Enter again the number of developers. The max team size is: " +  maxTeam + " ");
            numDevs = scanner.nextInt();
            scanner.nextLine();
        }

        ArrayList<String> CvsEntered = new ArrayList<>();
        HashMap <Integer, String> scoresEntered = new HashMap<>();

        int holidaysSummary = 0;
        int score;

        int averageHolidays = 0;
        for (int i = 0; i < numDevs; i++) {
            logger.info("Developer #" + (i + 1));
            logger.info("Name of developer " + (i + 1) + " ");
            String name = scanner.nextLine();

            CvsEntered.add(name);
            logger.info("Level jr/mid/sr (write one of the three options shown before without changes): ");
            String level = scanner.nextLine();

            while (!Objects.equals(level, "jr") && !Objects.equals(level, "mid") && !Objects.equals(level, "sr")){
                logger.error("WRONG ANSWER");
                logger.info("Your entry failed. Choose level jr/mid/sr (write one of the three options shown before without changes): ");
                level = scanner.nextLine();
            }

            logger.info("Hourly Rate: ");
            float rate = (float) scanner.nextDouble();
            scanner.nextLine();

            logger.info("Score in the test  (1-100) ");
            score = (int) scanner.nextInt();

            while (score<0 || score>100){
                logger.error("THE SCORE MUST BE (1-100) ");
                logger.info("Please, enter the score again ");
                score = (int) scanner.nextInt();
            }

            scoresEntered.put(score, name);

            logger.info("How may weeks of holidays are approved? ");
            int holidaysSchedule = scanner.nextInt();
            scanner.nextLine();

            holidaysSummary += holidaysSchedule;

            if (numDevs<=1){
                averageHolidays=holidaysSummary;
            }else{
                averageHolidays = holidaysSummary / (numDevs - 1);
            }

            try{
                devList.add(new Developer(name, level,(float) rate, holidaysSchedule));
            }catch(InvalidHourlyRateException | InvalidHolidaysException e){
                logger.error("Failed to create developer: " + e.getMessage() );
            }
        }

        Team team= null;
        try{
            team = new Team(devList, maxTeam);
        }catch(InvalidTeamSizeException e){
            logger.error("Invalid team size, cannot be negative: "+ e.getMessage());
        }

        for (Developer dev : devList) {
            dev.valueRole();
        }

        logger.info("Enter project duration in weeks: ");
        int weeks = scanner.nextInt();
        scanner.nextLine();

        logger.info("Enter hours per developer per week: ");
        int hoursPerWeek = scanner.nextInt();
        scanner.nextLine();

        ProjectProcess projectProcess = null;
        try {
            projectProcess = new ProjectProcess(hoursPerWeek, weeks, deadlineDelivery);
        } catch (InvalidProjectDeadlineException e) {
            logger.error("Invalid Deadline delivery, must be in format MM/DD/YYYY: " + e.getMessage());
        }
        double cost = 0;
        try {
            cost = CostEstimator.estimateCost(team, projectProcess, hoursPerWeek);
            scanner.nextLine();
        }catch(InvalidHoursPerDayException e) {
            logger.error("Invalid amount of hours" + e.getMessage());
        }

        logger.info("Enter technologies used (comma-separated): ");
        String techInput = scanner.nextLine();
        List<String> technologies = Arrays.asList(techInput.split("\\s*,\\s*"));

        int averageHolidaysWeeks;
        TimeOutForHire timeOutForHire = new TimeOutForHire(averageHolidays, CvsEntered, technologies);
        averageHolidaysWeeks = averageHolidays / devList.size();

        List<String> featuresList = new ArrayList<>();
        for (int i = 0; i < features; i++) {
            logger.info("Name of the feature " + (i + 1) + "?");
            String featureNameInput = scanner.nextLine();
            timeOutForHire.addSkill(featureNameInput);
            featuresList.add(featureNameInput);
        }

        for (Developer name : devList){
            timeOutForHire.queueCandidate(String.valueOf(name));
        }

        for (Map.Entry<Integer, String> entry :scoresEntered.entrySet()){
            logger.info(entry.getKey() + " -> " + entry.getValue());
            if (entry.getKey()>=60){
                timeOutForHire.approveCandidate(entry.getValue());
            }
        }

        int featureDaysInput = weeks * 7;
        Feature feature = new Feature(featureDaysInput, featuresList);

        List<TimeOutForHire.ITEmployee> itEmployees = new ArrayList<>();

        for (Developer dev : devList) {
            itEmployees.add(dev);
        }

        int totalHours=hoursPerWeek * weeks;

        Task task = new Task(projectName, totalHours);

        TechnologyStack techStack = new TechnologyStack(technologies, task.getName(), totalHours);

        List<StockProjects> stockList =new ArrayList<>();
        stockList.add(task);
        stockList.add(techStack);

        logger.info(" Stocking Projects ");

        for (StockProjects item : stockList){
            item.stockProjects();
        }

        logger.info("Enter manager name: ");
        String managerName = scanner.nextLine();
        logger.info("Enter manager hourly rate: ");
        float managerRate = scanner.nextFloat();
        logger.info("Enter manager level jr/mid/sr");
        String managerLevel=scanner.nextLine();
        while (!Objects.equals(managerLevel, "jr") && !Objects.equals(managerLevel, "mid") && !Objects.equals(managerLevel, "sr")){
            logger.error("WRONG ANSWER");
            logger.info("Your entry failed. Choose level jr/mid/sr (write one of the three options shown before without changes): ");
            managerLevel = scanner.nextLine();
        }
        logger.info("How many holidays for the manager (weeks)? ");
        int managerHolidays = scanner.nextInt();
        scanner.nextLine();

        try {
            Manager manager = new Manager(managerName, managerRate, managerLevel, managerHolidays);
            itEmployees.add(manager);
            manager.valueRole();
        } catch (InvalidHourlyRateException | InvalidHolidaysException e) {
            logger.error("Failed to create manager: " + e.getMessage());
        }

        logger.info("Enter the project scope (e.g., E-commerce Website, Mobile App, etc.):");
        String scopeName = scanner.nextLine();

        Scope projectScope = new Scope(scopeName);

        projectScope.setBudget(budget);

        logger.info("Choose advertising platform (e.g., Instagram, LinkedIn, TikTok):");
        String platform = scanner.nextLine();
        projectScope.choosePlatform(platform);

        projectScope.setTargetAudience(scope);

        projectScope.adsCreation();
        projectScope.getTargetAudience();

        logger.info("Ads creation process for '" + projectScope.getScope() + "' completed successfully!");

        assert team != null;
        Report report = new Report((float) cost, projectProcess.getWeeks(), team.getTeamSize(), timeOutForHire.averageHolidaysReplacement);

        logger.info("\n--- Project Summary ---");
        logger.info("Client: " + client.getName());
        logger.info("Idea: " + client.getIdeaDescription());
        logger.info("Project: " + project.getProjectName() + " (" + project.getComplexityLevel() + ")");
        logger.info( projectProcess.achievability() + " and " + project.achievability());
        logger.info("This project has a/an " + client.getScope() + " scope ");
        logger.info("Estimated Features: " + feature.getFeaturesList());
        logger.info("Technology Stack: " + techStack.getTechnologies());
        logger.info("You need to deliver this project before " + projectProcess.getDeadlineDelivery());

        logger.info("\n--- IT Employees Overview ---");
        for (TimeOutForHire.ITEmployee employee : itEmployees) {
            employee.itEmployee();
        }
        timeOutForHire.toolsManagement();
        report.printReport();
        StateIncentive<Report> stateIncentive =new StateIncentive<Report>();
        stateIncentive.setStateIncentive(report);
        logger.info("according to your costs, your project receives" + stateIncentive);
        client.discounts();

        logger.info(" On average the amount of holidays weeks per developer is: " + averageHolidaysWeeks);

        if (averageHolidaysWeeks>weeks){
            logger.info("Your timeline is at risk of getting delayed");
        }

        project.reOffer((float) cost);

        int daysUntilApprobation= getDaysUntilApprobation();

        for (String s : lawsIncluded()) {
            logger.info(s + " required");
        }

        logger.info("Remember that the final approbation of the project is required by law under " + daysUntilApprobation + " days ");

        scanner.close();
    }
}

