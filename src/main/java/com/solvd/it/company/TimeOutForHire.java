package com.solvd.it.company;

import com.solvd.it.compAbstract.HiringPlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class TimeOutForHire extends HiringPlatform {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(TimeOutForHire.class);

    private HashSet<String> skills;
    private LinkedList<String> interviewQueue;
    private HashMap<String, Integer> candidateScores;
    private TreeSet<String> approvedCandidates;

    public TimeOutForHire(int averageHolidays, ArrayList<String> CvsEntered, List<String> toolsManagement) {
        super(averageHolidays, CvsEntered, toolsManagement);
        this.skills = new HashSet<>();
        this.interviewQueue = new LinkedList<>();
        this.candidateScores = new HashMap<>();
        this.approvedCandidates = new TreeSet<>();

    }

    @Override
    public void curriculumDelivered() {
    }

    public List<String> toolsManagement() {
        for (String d : CvsEntered) {
            return Collections.singletonList(d + " must have knowledge about " + toolsManagement);
        }

        for (String skill : skills) {
            System.out.println(skill);
        }

        System.out.println("\n=== Interview Queue (LinkedList) ===");
        for (String candidate : interviewQueue) {
            System.out.println(candidate);
        }

        System.out.println("\n=== Candidate Scores (HashMap) ===");
        for (Map.Entry<String, Integer> entry : candidateScores.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("\n=== Approved Candidates (TreeSet - Sorted) ===");
        for (String candidate : approvedCandidates) {
            System.out.println(candidate);
        }
        return toolsManagement;
    }

    public interface ITEmployee {
        void itEmployee();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void queueCandidate(String candidate) {
        interviewQueue.add(candidate);
    }

    public void approveCandidate(String candidate) {
        approvedCandidates.add(candidate);
    }

    public void scoreCandidate(String candidate, int score){
        candidateScores.put(candidate, score);
    }
}
