install.packages("effsize")
library(effsize)

root.path<- "/home/qinghua/projects/matg/data/experiments"
datasets<-c(
    "HumanEvalJava","HumanEvalJava_mut_0","HumanEvalJava_mut_1","HumanEvalJava_mut_2",
    "Leetcode","Leetcode_mut_0","Leetcode_mut_1","Leetcode_mut_2"
    )
methods<-c("candor","empirical","evosuite","candor_no_planner","candor_no_requirement_engineer","candor_no_ensemble")
runs<-c("run_0","run_1","run_2")


candor_jacoco_csv_path<-paste(root.path,datasets[1],methods[1],runs[1],"generate","target/site/jacoco/jacoco.csv",sep="/")


get_file_and_data_coverage<-function(path){
    df<- read.csv(path, header=TRUE, sep=",", stringsAsFactors=FALSE)
    coverage_per_row <- get_coverage_per_row(df)
    total_coverage <- get_total_coverage(df)
    return(list(per_row = coverage_per_row, total = total_coverage))
}

get_coverage_per_row <- function(df) {
  branch_cov <- df$BRANCH_COVERED / (df$BRANCH_COVERED + df$BRANCH_MISSED)
  line_cov <- df$LINE_COVERED / (df$LINE_COVERED + df$LINE_MISSED)
  
  # Return a new dataframe with branch and line coverage columns
  coverage_df <- data.frame(
    CLASS= df$CLASS,
    BRANCH_COVERAGE = branch_cov,
    LINE_COVERAGE = line_cov
  )
  
  return(coverage_df)
}

get_total_coverage <- function(df) {
  total_branch_covered <- sum(df$BRANCH_COVERED, na.rm = TRUE)
  total_branch_missed <- sum(df$BRANCH_MISSED, na.rm = TRUE)
  total_line_covered <- sum(df$LINE_COVERED, na.rm = TRUE)
  total_line_missed <- sum(df$LINE_MISSED, na.rm = TRUE)
  
  total_branch_cov <- total_branch_covered / (total_branch_covered + total_branch_missed)
  total_line_cov <- total_line_covered / (total_line_covered + total_line_missed)
  
  return(data.frame(
    TOTAL_BRANCH_COVERAGE = total_branch_cov,
    TOTAL_LINE_COVERAGE = total_line_cov
  ))
}

# Calculate coverage per row
coverage_results <- get_file_and_data_coverage(candor_jacoco_csv_path)
candor_coverage_per_row <- coverage_results$per_row
candor_total_coverage <- coverage_results$total
# print(candor_coverage_per_row)
# Calculate total coverage
print(candor_total_coverage)


# evosuite
evosuite_jacoco_csv_path <- paste(root.path, datasets[1], methods[3], runs[3],  "target/site/jacoco/jacoco.csv", sep="/")
coverage_results_evosuite <- get_file_and_data_coverage(evosuite_jacoco_csv_path)
evosuite_coverage_per_row <- coverage_results_evosuite$per_row
evosuite_total_coverage <- coverage_results_evosuite$total
# print(evosuite_coverage_per_row)
# Calculate total coverage
print(evosuite_total_coverage)

# empirical
empirical_jacoco_csv_path <- paste(root.path, datasets[1], methods[2], "best",  "target/site/jacoco/jacoco.csv", sep="/")
coverage_per_row_empirical <- get_file_and_data_coverage(empirical_jacoco_csv_path)
empirical_coverage_per_row <- coverage_per_row_empirical$per_row
empirical_total_coverage <- coverage_per_row_empirical$total
# print(empirical_coverage_per_row)
# Calculate total coverage
print(empirical_total_coverage)


# wilxocon testing
### candor vs evosuite
merged <- merge(
  candor_coverage_per_row,
  evosuite_coverage_per_row,
  by = "CLASS",
  suffixes = c("_candor", "_evosuite")
)
# merged<-merged[!grepl("\\.", merged$CLASS), ]
candor_vs_evosuite_line<-wilcox.test(
  merged$LINE_COVERAGE_candor,
  merged$LINE_COVERAGE_evosuite,
  paired = TRUE
)
candor_vs_evosuite_branch<-wilcox.test(
  merged$BRANCH_COVERAGE_candor,
  merged$BRANCH_COVERAGE_evosuite,
  paired = TRUE,
)
print(candor_vs_evosuite_line)
print(candor_vs_evosuite_branch)

# A12 test
# Branch coverage A12
a12_branch <- VD.A(merged$BRANCH_COVERAGE_candor, merged$BRANCH_COVERAGE_evosuite)
print(a12_branch)

# Line coverage A12
a12_line <- VD.A(merged$LINE_COVERAGE_candor, merged$LINE_COVERAGE_evosuite)
print(a12_line)

### candor vs empirical
merged_empirical <- merge(
  candor_coverage_per_row,
  empirical_coverage_per_row,
  by = "CLASS",
  suffixes = c("_candor", "_empirical")
)
# merged_empirical<-merged_empirical[!grepl("\\.", merged_empirical$CLASS), ]
candor_vs_empirical_line <- wilcox.test(
  merged_empirical$LINE_COVERAGE_candor,
  merged_empirical$LINE_COVERAGE_empirical,
  paired = TRUE
)
candor_vs_empirical_branch <- wilcox.test(
  merged_empirical$BRANCH_COVERAGE_candor,
  merged_empirical$BRANCH_COVERAGE_empirical,
  paired = TRUE
)
print(candor_vs_empirical_line)
print(candor_vs_empirical_branch)
# A12 test
# Branch coverage A12
a12_branch_empirical <- VD.A(merged_empirical$BRANCH_COVERAGE_candor, merged_empirical$BRANCH_COVERAGE_empirical)
print(a12_branch_empirical)
# Line coverage A12
a12_line_empirical <- VD.A(merged_empirical$LINE_COVERAGE_candor, merged_empirical$LINE_COVERAGE_empirical)
print(a12_line_empirical)
# Effect size calculation

evosuite_human_mutation_scores<-read.csv("/home/qinghua/projects/matg/evosuite_0_mutantion_score.csv", header=TRUE, sep=",", stringsAsFactors=FALSE)
average_score <- mean(evosuite_human_mutation_scores$mutation_score, na.rm = TRUE)
evosuite_total_killed<- sum(evosuite_human_mutation_scores$killed, na.rm = TRUE)
evosuite_total_mutants <- sum(evosuite_human_mutation_scores$total, na.rm = TRUE)
print(paste("Average mutation score for Evosuite:", average_score))
print(paste("Total killed mutants for Evosuite:", evosuite_total_killed))
print(paste("Total mutants for Evosuite:", evosuite_total_mutants))
# Print the result
candor_human_mutation_scores <- read.csv("/home/qinghua/projects/matg/candor_0_mutantion_score.csv", header=TRUE, sep=",", stringsAsFactors=FALSE)
average_score_candor <- mean(candor_human_mutation_scores$mutation_score, na.rm = TRUE)
print(paste("Average mutation score for Candor:", average_score_candor))
candor_total_killed <- sum(candor_human_mutation_scores$killed, na.rm = TRUE)
candor_total_mutants <- sum(candor_human_mutation_scores$total, na.rm = TRUE)
print(paste("Total killed mutants for Candor:", candor_total_killed))
print(paste("Total mutants for Candor:", candor_total_mutants))

## wilcoxon test for mutation scores
merged_mutation_scores <- merge(
  candor_human_mutation_scores,
  evosuite_human_mutation_scores,
  by = "CLASS",
  suffixes = c("_candor", "_evosuite")
)
candor_vs_evosuite_mutation <- wilcox.test(
  merged_mutation_scores$mutation_score_candor,
  merged_mutation_scores$mutation_score_evosuite,
  paired = TRUE
)
print(candor_vs_evosuite_mutation)
candor_vs_empirical_mutation <- wilcox.test(
  merged_mutation_scores$mutation_score_candor,
  merged_mutation_scores$mutation_score_evosuite,
  paired = TRUE
)
print(candor_vs_empirical_mutation)
# Effect size A12 calculation
a12_mutation <- VD.A(
  merged_mutation_scores$mutation_score_candor,
  merged_mutation_scores$mutation_score_evosuite
)
print(a12_mutation)







# Leetcode dataset

### evosuite Leetcode
evosuite_jacoco_csv_path_leetcode <- paste(root.path, datasets[5], methods[3], runs[1], "target/site/jacoco/jacoco.csv", sep="/")
coverage_results_evosuite_leetcode <- get_file_and_data_coverage(evosuite_jacoco_csv_path_leetcode)
evosuite_coverage_per_row_leetcode <- coverage_results_evosuite_leetcode$per_row
evosuite_total_coverage_leetcode <- coverage_results_evosuite_leetcode$total
# print(evosuite_coverage_per_row_leetcode)
# Calculate total coverage for Leetcode
print(paste("Evosuite total coverage for Leetcode:", evosuite_total_coverage_leetcode))

# calculate coverage by difficulty
class_difficulty<-read.csv("/home/qinghua/projects/matg/leetcode_class_difficulty_mapping.csv", header=TRUE, sep=",", stringsAsFactors=FALSE)
# Merge coverage data with class difficulty
evosuite_coverage_per_row_leetcode <- merge(
  evosuite_coverage_per_row_leetcode,
  class_difficulty,
  by = "CLASS",
  all.x = TRUE
)
# Calculate average coverage by difficulty
average_coverage_by_difficulty <- aggregate(
  cbind(BRANCH_COVERAGE, LINE_COVERAGE) ~ DIFFICULTY,
  data = evosuite_coverage_per_row_leetcode,
  FUN = mean
)
print(average_coverage_by_difficulty)
# Wilcoxon test for Leetcode


## evosuite leetcode mutation scores
evosuite_leetcode_human_mutation_scores <- read.csv("/home/qinghua/projects/matg/evosuite_leetcode_0_mutantion_score.csv", header=TRUE, sep=",", stringsAsFactors=FALSE)
average_score_evosuite_leetcode <- mean(evosuite_leetcode_human_mutation_scores$mutation_score, na.rm = TRUE)
print(paste("Average mutation score for Evosuite Leetcode:", average_score_evosuite_leetcode))
evosuite_total_killed_leetcode <- sum(evosuite_leetcode_human_mutation_scores$killed, na.rm = TRUE)
evosuite_total_mutants_leetcode <- sum(evosuite_leetcode_human_mutation_scores$total, na.rm = TRUE) # nolint
print(paste("Total killed mutants for Evosuite Leetcode:", evosuite_total_killed_leetcode)) # nolint
print(paste("Total mutants for Evosuite Leetcode:", evosuite_total_mutants_leetcode))
## evosuite leetcode mutation scores grouped by difficulty
evosuite_leetcode_human_mutation_scores <- merge(
  evosuite_leetcode_human_mutation_scores,
  class_difficulty,
  by = "CLASS",
  all.x = TRUE
)
# Calculate average mutation score by difficulty
average_mutation_score_by_difficulty <- aggregate(
  mutation_score ~ DIFFICULTY,
  data = evosuite_leetcode_human_mutation_scores,
  FUN = mean
)
print(average_mutation_score_by_difficulty)
