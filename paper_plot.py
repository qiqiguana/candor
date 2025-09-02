import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

# Data
names = ["HumanEvalJava", "Leetcode-Medium", "Leetcode-Hard"]
candor_correct = [0.971, 0.993, 0.961]
empirical_correct = [0.813, 0.884, 0.825]
candor_incorrect = [0.942, 0.977, 0.952]
empirical_incorrect = [0.774, 0.866, 0.789]

# Prepare long-form DataFrame
correct_df = pd.DataFrame({
    "Dataset": names * 2,
    "Method": ["candor"] * 3 + ["empirical"] * 3,
    "Proportion": candor_correct + empirical_correct,
    "Type": ["Correct"] * 6
})
incorrect_df = pd.DataFrame({
    "Dataset": names * 2,
    "Method": ["candor"] * 3 + ["empirical"] * 3,
    "Proportion": candor_incorrect + empirical_incorrect,
    "Type": ["Incorrect"] * 6
})
df = pd.concat([correct_df, incorrect_df])

# Set style
sns.set(style="whitegrid", font_scale=1.1)

# Create subplots
fig, axes = plt.subplots(2, 1, figsize=(6, 5), sharex=True)

# Plot Correct
sns.barplot(
    ax=axes[0], data=correct_df,
    x="Dataset", y="Proportion", hue="Method",
    palette="muted", width=0.5
)
axes[0].set_title("Correct Predictions")
axes[0].set_ylim(0, 1.0)
axes[0].set_ylabel("Proportion")
axes[0].legend_.remove()

# Plot Incorrect
sns.barplot(
    ax=axes[1], data=incorrect_df,
    x="Dataset", y="Proportion", hue="Method",
    palette="muted", width=0.5
)
axes[1].set_title("Incorrect Predictions")
axes[1].set_ylim(0, 1.0)
axes[1].set_ylabel("Proportion")
axes[1].set_xlabel("Dataset")

# Shared legend
handles, labels = axes[1].get_legend_handles_labels()
fig.legend(handles, labels, title="Method", loc="upper center", ncol=2, bbox_to_anchor=(0.5, 1.08))

# Adjust layout
plt.tight_layout(rect=[0, 0, 1, 0.95])
plt.savefig("clean_prediction_bars_narrow.pdf", format="pdf")
plt.show()
