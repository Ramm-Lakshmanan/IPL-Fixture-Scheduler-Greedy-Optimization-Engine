# IPL Fixture Scheduler — Greedy Optimization Engine

A complete Java reimplementation and enhancement of an IPL Scheduling System originally built as a collaborative academic project in C. This version redesigns the system from the ground up using object-oriented principles and a modular architecture, replacing basic fixture generation with constraint-based scheduling, greedy optimization, and priority-queue-driven candidate selection.

## Motivation

The original C project handled basic round-robin fixture generation and tournament management. This repository was built to go deeper — treating fixture scheduling as an optimization problem rather than a simple allocation task, and using it as a space to explore scheduling algorithms, cost modeling, and clean software design end-to-end in Java.

## Overview

Given a set of teams, home venues, and a city distance matrix, the scheduler generates a double round-robin fixture list while minimizing travel and enforcing fairness constraints (rest days, home/away balance, venue availability). Instead of a fixed allocation loop, each calendar slot is filled by evaluating all valid candidate matches, scoring them with a custom cost function, and greedily picking the lowest-cost option via a min-heap.

## How It Works

1. Generate double round-robin fixtures (every team plays every other team home and away).
2. Build the tournament calendar (days and available slots).
3. Build a city distance matrix between team home venues.
4. For each empty calendar slot, collect all currently unscheduled matches.
5. Validate hard constraints for each candidate match.
6. Compute a cost for every valid candidate.
7. Push valid candidates into a priority queue (min-heap) ordered by cost.
8. Pop the minimum-cost match and assign it to the slot.
9. Update both teams' state.
10. Repeat until every slot is filled.
11. Output the final fixture list.

### Fixture Math

| Format | Formula | IPL (10 teams) |
|---|---|---|
| Single round-robin matches | n(n-1)/2 | 45 |
| Double round-robin matches | n(n-1) | 90 |
| Single round-robin rounds | n-1 | 9 |
| Double round-robin rounds | 2(n-1) | 18 |

### Constraint Validation

Before a candidate match is even scored, it must pass:
- The match hasn't already been scheduled
- Both teams are free on that day
- Minimum rest days since each team's last match is satisfied
- The venue is available
- Neither team exceeds the maximum allowed consecutive home/away streak

### Cost Function

Only constraint-valid candidates are scored:
Cost = TravelCost + RestPenalty + HomeAwayPenalty + PriorityPenalty

- **TravelCost** — distance between a team's last venue and the candidate venue
- **RestPenalty** — 0 if rest requirement is met, a very large value otherwise (acts as a soft hard-constraint)
- **HomeAwayPenalty** — penalizes long home or away streaks to keep schedules balanced
- **PriorityPenalty** — optional adjustment to favor marquee or weekend matches

### Team State Tracked

Per team, updated after every assignment:
- `lastPlayedDay`
- `lastVenue`
- `homeStreak`
- `awayStreak`
- `totalTravel`

## Tech Stack

Java, Core DSA (Priority Queue / Min-Heap, Greedy Algorithms), Collections Framework

## Relationship to the Original Project

This is an independent, from-scratch Java rewrite inspired by an earlier C-based academic group project. No code is shared between the two — the algorithmic core (cost-based greedy scheduling with a priority queue) and the object-oriented architecture here are new.

## Future Enhancements

- Playoff scheduling module (Qualifiers, Eliminator, Final)
- Configurable weights for the cost function
- Export schedule to file/JSON
- Basic CLI for viewing team-specific or venue-specific fixtures
