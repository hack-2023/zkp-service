package com.hack2023.zkp_service.domain.login;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ChallengeData {
    private int minChallengeRounds = 50;
    private int maxChallengeRounds = 100;
    private String email;
    private int hashLength;
    private int challengeLength;
    private int round;
    private int totalRounds;
    private Set<Integer> indices;
    private String dataFromIndices;

    public ChallengeData() {}
    public ChallengeData(int minChallengeRounds, int maxChallengeRounds) {
        this.minChallengeRounds = minChallengeRounds;
        this.maxChallengeRounds = maxChallengeRounds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHashLength() {
        return hashLength;
    }

    public void setHashLength(int hashLength) {
        this.hashLength = hashLength;
    }

    public int getChallengeLength() {
        return (ThreadLocalRandom.current().nextInt(getHashLength()/2, getHashLength()));
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int computeTotalRounds() {
        return ThreadLocalRandom.current().nextInt(minChallengeRounds, maxChallengeRounds);
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public Set<Integer> getIndices() {
        return new Random().ints(0, getHashLength())
                .distinct()
                .limit(getChallengeLength())
                .boxed()
                .collect(Collectors.toSet());
    }

    public List<Integer> getIndicesAsList() {
        return List.copyOf(getIndices());
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getDataFromIndices() {
        return dataFromIndices;
    }

    public void setDataFromIndices(String dataFromIndices) {
        this.dataFromIndices = dataFromIndices;
    }
}
