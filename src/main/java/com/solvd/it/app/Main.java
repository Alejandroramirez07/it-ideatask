package com.solvd.it.app;

import com.solvd.it.annotations.CheckBeforeDelivery;
import com.solvd.it.annotations.PriorityToRun;
import com.solvd.it.compInterfaces.StockProjects;
import com.solvd.it.company.*;
import com.solvd.it.dao.implementation.SplunkMonitoringDAOImpl;
import com.solvd.it.exceptions.*;
import com.solvd.it.enums.*;
import com.solvd.it.functionalInterfaces.ApprobationHours;
import com.solvd.it.functionalInterfaces.HolidaysThreshold;
import com.solvd.it.functionalInterfaces.RecommendedOS;
import com.solvd.it.monitoring.SplunkMonitoring;
import com.solvd.it.record.Feature;
import com.solvd.it.record.LawRequirements;
import com.solvd.it.threads.ProjectRecommendationsThread;
import com.solvd.it.threads.SecRecommendationsThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.solvd.it.company.Team.maxTeam;

import static java.util.Arrays.stream;


public class Main {
    public static void main(String[] args) {
        final Logger LOGGER = LogManager.getLogger(Main.class);

        InputReader inputReader = new InputReader();
        inputReader.processFile();

        SecRecommendationsThread secRecommendationsThread =new SecRecommendationsThread();
        ProjectRecommendationsThread projectRecommendationsThread = new ProjectRecommendationsThread();

        secRecommendationsThread.start();
        projectRecommendationsThread.start();

        Scanner scanner = new Scanner(System.in);

        LOGGER.debug("User prompted to enter project information ");
        LOGGER.info("Enter client name");
        String clientName = scanner.nextLine();
        boolean isValidClient = Client.isValidClientName(clientName);

        while (!isValidClient) {
            LOGGER.info("Your last Project name " + clientName +  " is too short, Enter project name with minimum length of 3: ");
            clientName = scanner.nextLine();
            isValidClient = Client.isValidClientName(clientName);
        }

        LOGGER.info("Describe the project idea: ");
        String ideaDescription = scanner.nextLine();
        boolean isDescriptionValid = Client.isValidIdeaDescription(ideaDescription);

        while (!isDescriptionValid) {
            LOGGER.error("WRONG ANSWER");
            LOGGER.info("Your last Project description " + ideaDescription +  " is too short, Enter project name with minimum length of 11: ");
            ideaDescription = scanner.nextLine();
            isDescriptionValid = Client.isValidIdeaDescription(ideaDescription);
        }

        LOGGER.info("Is the project National or International (nat/inter) ");
        String scope = scanner.nextLine().toLowerCase(Locale.ROOT);

        while (!Objects.equals(scope, "nat") && !Objects.equals(scope, "inter")){
            LOGGER.error("WRONG ANSWER");
            LOGGER.info("Please, write the scope in terms of nat or inter");
            scope = scanner.nextLine();
        }

        Client client = new Client(clientName, ideaDescription, scope);

        LOGGER.info("Enter project name: ");
        String projectName = scanner.nextLine();
        boolean isNameValid = Project.isValidProjectName(projectName);

        while (!isNameValid) {
            LOGGER.error("WRONG ANSWER");
            LOGGER.info("Your last Project name " + projectName +  " is too short, Enter project name with minimum length of 4: ");
            projectName = scanner.nextLine();
            isNameValid = Project.isValidProjectName(projectName);
        }

        LOGGER.info("Enter project complexity (low / mid / high): ");
        String complexity = scanner.nextLine();

        while (!Objects.equals(complexity, "low") && !Objects.equals(complexity, "mid") && !Objects.equals(complexity, "high")){
            LOGGER.error("WRONG ANSWER");
            LOGGER.info("Your entry failed. Choose complexity level low/mid/high (write one of the three options shown before without changes): ");
            complexity = scanner.nextLine();
        }

        int features;

        while(true) {
            try {
                LOGGER.info("Enter estimated number of features: ");
                features = scanner.nextInt();
                scanner.nextLine();
                break;
            }catch (InputMismatchException e){
                LOGGER.info("input must be an Integer " + e.getMessage() + " Please, try again");
                scanner.nextLine();
            }
        }

        LOGGER.info("Enter initial budget: ");
        if (scanner.hasNextLine()) scanner.nextLine();
        float budget = 0f;
        try {
            String budgetInput = scanner.nextLine().trim();
            budget = Float.parseFloat(budgetInput);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input for budget: " + e.getMessage());
        }
        scanner.nextLine();

        LOGGER.info("Enter the deadline date for this project MM/DD/YYYY");
        LocalDate deadlineDelivery = null;
        try {
            String deadlineInput = scanner.nextLine().trim();
            deadlineDelivery = LocalDate.parse(deadlineInput, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Invalid input for deadline date: " + e.getMessage());
            deadlineDelivery = LocalDate.now().plusWeeks(4);
        }


        Project project = new Project(projectName, complexity, budget, clientName);

        List<Developer> devList = new ArrayList<>();

        LOGGER.info("Enter number of developers: ");
        int numDevs = 0;
        try {
            numDevs = Integer.parseInt(scanner.nextLine().trim());
            if (numDevs <= 0) {
                LOGGER.error("Invalid team size, cannot be negative: A team must have at least one developer.");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input for number of developers: " + e.getMessage());
        }


        while (numDevs>maxTeam){
            LOGGER.error("WRONG ANSWER");
            LOGGER.info("Enter again the number of developers. The max team size is: " +  maxTeam + " ");
            numDevs = scanner.nextInt();
            scanner.nextLine();
        }

        ArrayList<String> CvsEntered = new ArrayList<>();
        HashMap <Integer, String> scoresEntered = new HashMap<>();

        int holidaysSummary = 0;
        int score;

        int averageHolidays = 0;
        for (int i = 0; i < numDevs; i++) {
            LOGGER.info("Developer #" + (i + 1));
            LOGGER.info("Name of developer " + (i + 1) + " ");
            String name = scanner.nextLine();

            CvsEntered.add(name);
            LOGGER.info("Level jr/mid/sr (write one of the three options shown before without changes): ");
            String level = scanner.nextLine();

            while (!Objects.equals(level, "jr") && !Objects.equals(level, "mid") && !Objects.equals(level, "sr")){
                LOGGER.error("WRONG ANSWER");
                LOGGER.info("Your entry failed. Choose level jr/mid/sr (write one of the three options shown before without changes): ");
                level = scanner.nextLine();
            }

            LOGGER.info("Hourly Rate: ");
            float rate = (float) scanner.nextDouble();
            scanner.nextLine();

            LOGGER.info("Score in the test  (1-100) ");
            score = scanner.nextInt();

            while (score<0 || score>100){
                LOGGER.error("THE SCORE MUST BE (1-100) ");
                LOGGER.info("Please, enter the score again ");
                score = scanner.nextInt();
            }

            scoresEntered.put(score, name);

            LOGGER.info("How may weeks of holidays are approved? ");
            int holidaysSchedule = scanner.nextInt();
            scanner.nextLine();

            holidaysSummary += holidaysSchedule;

            if (numDevs<=1){
                averageHolidays=holidaysSummary;
            }else{
                averageHolidays = holidaysSummary / (numDevs - 1);
            }

            try{
                devList.add(new Developer(name, level, rate, holidaysSchedule));
            }catch(InvalidHourlyRateException | InvalidHolidaysException e){
                LOGGER.error("Failed to create developer: " + e.getMessage() );
            }
        }

        Team team= null;
        try{
            team = new Team(devList, maxTeam);
        }catch(InvalidTeamSizeException e){
            LOGGER.error("Invalid team size, cannot be negative: {}", e.getMessage());
        }

        devList.stream()
                .forEach(Developer::valueRole);

        LOGGER.info("Enter project duration in weeks: ");
        int weeks=0;
        try {
            weeks = Integer.parseInt(scanner.nextLine().trim());
            if (weeks <= 0) {
                LOGGER.error("Invalid project duration: Must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input for project duration: " + e.getMessage());
        }

        LOGGER.info("Enter hours per developer per week: ");
        int hoursPerWeek=0;
        try {
            hoursPerWeek = Integer.parseInt(scanner.nextLine().trim());
            if (hoursPerWeek <= 0) {
                LOGGER.error("Invalid hours per week: Must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input for hours per week: " + e.getMessage());
        }

        ProjectProcess projectProcess = null;
        try {
            projectProcess = new ProjectProcess(hoursPerWeek, weeks, deadlineDelivery);
        } catch (InvalidProjectDeadlineException e) {
            LOGGER.error("Invalid Deadline delivery, must be in format MM/DD/YYYY: " + e.getMessage());
        }
        double cost = 0;
        try {
            cost = CostEstimator.estimateCost(team, projectProcess, hoursPerWeek);
            scanner.nextLine();
        }catch(InvalidHoursPerDayException e) {
            LOGGER.error("Invalid amount of hours" + e.getMessage());
        }

        LOGGER.info("Enter technologies used (comma-separated): ");
        String techInput = scanner.nextLine();
        List<String> technologies = Arrays.asList(techInput.split("\\s*,\\s*"));

        int averageHolidaysWeeks;
        TimeOutForHire timeOutForHire = new TimeOutForHire(averageHolidays, CvsEntered, technologies);

        if (devList.size()>0){
            averageHolidaysWeeks = averageHolidays / devList.size();
        }else{
            averageHolidaysWeeks=averageHolidays;
        }

        List<String> featuresList = new ArrayList<>();
        for (int i = 0; i < features; i++) {
            LOGGER.info("Name of the feature " + (i + 1) + "?");
            String featureNameInput = scanner.nextLine();
            timeOutForHire.addSkill(featureNameInput);
            featuresList.add(featureNameInput);
        }

        devList.stream()
                .map(String::valueOf)
                .forEach(timeOutForHire::queueCandidate);

        scoresEntered.entrySet().stream()
                .peek(entry -> LOGGER.info(entry.getKey() + " -> " + entry.getValue()))
                .filter(entry -> entry.getKey() >= 60)
                .map(Map.Entry::getValue)
                .forEach(timeOutForHire::approveCandidate);


        int featureDaysInput = weeks * 7;
        Feature feature = new Feature(featureDaysInput, featuresList);

        List<TimeOutForHire.ITEmployee> itEmployees = new ArrayList<>();

        devList.stream()
                .forEach(itEmployees::add);

        int totalHours=hoursPerWeek * weeks;

        Task task = new Task(projectName, totalHours);

        TechnologyStack techStack = new TechnologyStack(technologies, task.getName(), totalHours);

        List<StockProjects> stockList =new ArrayList<>();
        stockList.add(task);
        stockList.add(techStack);

        LOGGER.info(" Stocking Projects ");

        stockList.stream()
                .forEach(StockProjects::stockProjects);

        LOGGER.info("Enter manager name: ");
        String managerName = scanner.nextLine();
        LOGGER.info("Enter manager hourly rate: ");
        while (!scanner.hasNextFloat()) {
            String badInput = scanner.nextLine();
            if (!badInput.isBlank()) {
                LOGGER.error("Invalid number input for hourly rate: {}", badInput);
            }
        }
        float managerRate = scanner.nextFloat();
        LOGGER.info("Enter manager level jr/mid/sr");
        String managerLevel=scanner.nextLine();
        while (!Objects.equals(managerLevel, "jr") && !Objects.equals(managerLevel, "mid") && !Objects.equals(managerLevel, "sr")){
            LOGGER.error("WRONG ANSWER");
            LOGGER.info("Your entry failed. Choose level jr/mid/sr (write one of the three options shown before without changes): ");
            managerLevel = scanner.nextLine();
        }
        LOGGER.info("How many holidays for the manager (weeks)? ");
        int managerHolidays = scanner.nextInt();
        scanner.nextLine();

        try {
            Manager manager = new Manager(managerName, managerRate, managerLevel, managerHolidays);
            itEmployees.add(manager);
            manager.valueRole();
        } catch (InvalidHourlyRateException | InvalidHolidaysException e) {
            LOGGER.error("Failed to create manager: " + e.getMessage());
        }

        LOGGER.info("Your IT project may be used with the next cloud services and availability levels");

        stream(CloudServices.values())
                .peek(cloudServices -> LOGGER.info(cloudServices + " with an availability level of: "))
                .forEach(cloudServices -> LOGGER.info(String.valueOf(cloudServices.cloudAvailability)));

        LOGGER.info("You may choose one of the next types of architecture for your project, choose from 1 to 5 only");

        int option=1;
        for(ArchitectureTypes architectureTypes: ArchitectureTypes.values()){
            LOGGER.info(Optional.of(option));
            LOGGER.info(architectureTypes);
            LOGGER.info("with a complexity level of ");
            LOGGER.info(Optional.of(architectureTypes.maintenanceLevelArchitecture));
            option ++;
        }

        boolean validArchitecture = false;

        while (!validArchitecture) {
            try {
                System.out.print("Enter an option (1-5): ");
                int optionchosen = scanner.nextInt();

                if (optionchosen >= 1 && optionchosen <= 5) {
                    if (optionchosen < 3) {
                        float optionExtra = (float) (cost * 0.1);
                        LOGGER.info("Due to the type of architecture desired, there could be an extra cost of " + optionExtra);
                    }
                    validArchitecture = true;
                } else {
                    LOGGER.error("Invalid input. Please enter a valid option (1–5).");
                }

            } catch (InputMismatchException e) {
                LOGGER.error("Invalid input. Please enter a number.", e);
                scanner.nextLine();
            }
        }

        LOGGER.info("Please, enter the project code");
        int projectCode=scanner.nextInt();

        LOGGER.info("Please, enter the comments from the monitor");
        String monitorComments= scanner.nextLine();

        int numberIncidents = 0;
        boolean validIncidents = false;

        while (!validIncidents) {
            LOGGER.info("Please, enter the number of incidents");

            String input = scanner.nextLine().trim();
            try {
                numberIncidents = Integer.parseInt(input);
                validIncidents = true;
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid input for number of incidents: {}", input);
            }
        }

        SplunkMonitoring splunkMonitoring = new SplunkMonitoring(projectCode, monitorComments, numberIncidents);
        SplunkMonitoringDAOImpl splunkMonitoringDAOImpl =new SplunkMonitoringDAOImpl();
        splunkMonitoringDAOImpl.save(splunkMonitoring);

        splunkMonitoringDAOImpl.findAll();

        LOGGER.info("if you wanto to delete a project from monitoring press 1, if you dont, press any other key");
        int optionDeletingMonitoring=scanner.nextInt();

        if (optionDeletingMonitoring==1){
            LOGGER.info("please type the project_code to delete");
            int projectToDeleteMonitoring=scanner.nextInt();
            splunkMonitoringDAOImpl.findById(projectToDeleteMonitoring);
        }




        LOGGER.info("Enter the project scope (e.g., E-commerce Website, Mobile App, etc.):");
        String scopeName = scanner.nextLine();

        Scope projectScope = new Scope(scopeName);

        projectScope.setBudget(budget);

        LOGGER.info("Choose advertising platform (e.g., Instagram, LinkedIn, TikTok):");
        String platform = scanner.nextLine();
        projectScope.choosePlatform(platform);

        projectScope.setTargetAudience(scope);

        projectScope.adsCreation();
        projectScope.getTargetAudience();

        LOGGER.info("Ads creation process for '" + projectScope.getScope() + "' completed successfully!");

        if (team == null) {
            LOGGER.error("Team object was not initialized properly. Skipping project summary generation.");
            return;
        }

        if (projectProcess == null) {
            LOGGER.error("ProjectProcess was not created due to invalid input or missing data.");
            return;
        }
        Report report = new Report((float) cost, projectProcess.getWeeks(), team.getTeamSize(), timeOutForHire.averageHolidaysReplacement);

        LOGGER.info("\n--- Project Summary ---");
        LOGGER.info("Client: " + client.getName());
        LOGGER.info("Idea: " + client.getIdeaDescription());
        LOGGER.info("Project: " + project.getProjectName() + " (" + project.getComplexityLevel() + ")");
        LOGGER.info( projectProcess.achievability() + " and " + project.achievability());
        LOGGER.info("This project has a/an " + client.getScope() + " scope ");
        LOGGER.info("Estimated Features: " + feature.featureList());
        LOGGER.info("Technology Stack: " + techStack.getTechnologies());
        LOGGER.info("You need to deliver this project before " + projectProcess.getDeadlineDelivery());

        LOGGER.info("\n--- IT Employees Overview ---");

        itEmployees.stream()
                .forEach(TimeOutForHire.ITEmployee::itEmployee);

        timeOutForHire.toolsManagement();

        if (report.getClass().isAnnotationPresent(CheckBeforeDelivery.class)){
            report.printReport();
        }else{
            LOGGER.error("Lack of annotation to check");
        }

        StateIncentive<Report> stateIncentive =new StateIncentive<Report>();
        stateIncentive.setStateIncentive(report);
        LOGGER.info("according to your costs, your project receives" + stateIncentive);
        client.discounts();

        for (Method method:  client.getClass().getDeclaredMethods()){
            if (method.isAnnotationPresent(PriorityToRun.class)){
                LOGGER.info("Client will get the offer in time if needed");
                try {
                    method.invoke(client);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }else {
                LOGGER.error("discount should have priority");
            }

        }

        RecommendedOS recommendedOS = (operativeSystems) -> {
            LOGGER.info("Recommended Operative systems: " + operativeSystems);
        };

        recommendedOS.listOperativeSystem("Windows 10, Ubuntu 22, MacOs Ventura");

        LOGGER.info(" On average the amount of holidays weeks per developer is: " + averageHolidaysWeeks);

        if (averageHolidaysWeeks>weeks){
            LOGGER.info("Your timeline is at risk of getting delayed");
        }

        HolidaysThreshold holidaysThreshold = () -> {
            if (averageHolidaysWeeks>=3){LOGGER.info(" The average weeks of holidays is getting too high");
            }};

        project.reOffer(Float.valueOf((float) cost));

        int daysUntilApprobation= LawRequirements.daysUntilApprobation;

        ApprobationHours approbationHours = (daysApprobation) -> {
            float workHoursUntilApprobation = daysApprobation * 8;
            LOGGER.info("The work hours required to approve the project are: " + workHoursUntilApprobation);
            return workHoursUntilApprobation;
        };

        approbationHours.approbationHours(daysUntilApprobation);

        LawRequirements.lawsIncluded.stream()
                .forEach(s -> LOGGER.info(" required"));

        LOGGER.info("If you want to have a visit at our physical locations, we have plenty of options to visit our offices: ");

        Arrays.stream(Offices.values())
                .forEach(offices -> LOGGER.info(offices.officeAddress));

        LOGGER.info("Remember that the final approbation of the project is required by law under " + daysUntilApprobation + " days ");

        scanner.close();
    }
}

