import os
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import ScalarFormatter

CSV_FILE = "results/results.csv"
OUTPUT_DIR = "results/plots"

SIZES = [100, 1_000, 10_000, 100_000]

os.makedirs(OUTPUT_DIR, exist_ok=True)

df = pd.read_csv(CSV_FILE)


def setup_x_axis(ax):
    ax.set_xscale("log")
    ax.set_xticks(SIZES)
    ax.get_xaxis().set_major_formatter(ScalarFormatter())
    ax.set_xlabel("n (input size)")
    ax.grid(True, which="both", alpha=0.3)


def set_metric_scale(ax, values):
    values = list(values)

    if all(value > 0 for value in values):
        ax.set_yscale("log")
    elif any(value > 0 for value in values):
        # Allows zero values while still giving log-like spacing.
        ax.set_yscale("symlog", linthresh=1)


def plot_workload(workload, filename):
    data = df[df["workload"] == workload].copy()

    fig, axes = plt.subplots(2, 2, figsize=(13, 9))

    time_ax = axes[0, 0]
    steps_ax = axes[0, 1]
    moves_ax = axes[1, 0]
    comparisons_ax = axes[1, 1]

    if workload == "W3":
        groups = data.groupby(["structure", "variant"])

        for (structure, variant), group in groups:
            group = group.sort_values("n")
            label = f"{structure} / {variant}"

            time_ax.plot(group["n"], group["time_ms"],
                         marker="o", label=label)

            steps_ax.plot(group["n"], group["steps"],
                          marker="o", label=label)

            moves_ax.plot(group["n"], group["moves"],
                          marker="o", label=label)

            comparisons_ax.plot(group["n"], group["comparisons"],
                                marker="o", label=label)

    else:
        groups = data.groupby("structure")

        for structure, group in groups:
            group = group.sort_values("n")

            time_ax.plot(group["n"], group["time_ms"],
                         marker="o", label=structure)

            steps_ax.plot(group["n"], group["steps"],
                          marker="o", label=structure)

            moves_ax.plot(group["n"], group["moves"],
                          marker="o", label=structure)

            comparisons_ax.plot(group["n"], group["comparisons"],
                                marker="o", label=structure)

    # Time
    setup_x_axis(time_ax)
    time_ax.set_yscale("log")
    time_ax.set_ylabel("Time (ms)")
    time_ax.set_title("Time vs n")
    time_ax.legend()

    # Steps
    setup_x_axis(steps_ax)
    set_metric_scale(steps_ax, data["steps"])
    steps_ax.set_ylabel("Steps")
    steps_ax.set_title("Steps vs n")
    steps_ax.legend()

    # Moves
    setup_x_axis(moves_ax)
    set_metric_scale(moves_ax, data["moves"])
    moves_ax.set_ylabel("Moves")
    moves_ax.set_title("Moves vs n")
    moves_ax.legend()

    # Comparisons
    setup_x_axis(comparisons_ax)
    set_metric_scale(comparisons_ax, data["comparisons"])
    comparisons_ax.set_ylabel("Comparisons")
    comparisons_ax.set_title("Comparisons vs n")
    comparisons_ax.legend()

    titles = {
        "W1": "W1 - Random Access",
        "W2": "W2 - Search",
        "W3": "W3 - Insert & Remove",
        "W4": "W4 - Priority Processing"
    }

    fig.suptitle(titles[workload], fontsize=16)
    fig.tight_layout(rect=[0, 0, 1, 0.96])

    path = os.path.join(OUTPUT_DIR, filename)
    plt.savefig(path, dpi=200, bbox_inches="tight")
    plt.close()

    print(f"Saved {path}")


plot_workload("W1", "w1_random_access.png")
plot_workload("W2", "w2_search.png")
plot_workload("W3", "w3_insert_remove.png")
plot_workload("W4", "w4_priority_processing.png")

print("All plots generated.")

bonus = pd.read_csv("results/buildheap_results.csv")

fig, axes = plt.subplots(1, 2, figsize=(12, 5))

for method, group in bonus.groupby("method"):
    group = group.sort_values("n")

    axes[0].plot(
        group["n"],
        group["time_ms"],
        marker="o",
        label=method
    )

    axes[1].plot(
        group["n"],
        group["comparisons"],
        marker="o",
        label=method
    )

setup_x_axis(axes[0])
axes[0].set_yscale("log")
axes[0].set_ylabel("Time (ms)")
axes[0].set_title("Build Time vs n")
axes[0].legend()

setup_x_axis(axes[1])
axes[1].set_yscale("log")
axes[1].set_ylabel("Comparisons")
axes[1].set_title("Comparisons vs n")
axes[1].legend()

fig.suptitle("Bonus - Floyd buildHeap vs Repeated Insert", fontsize=16)
fig.tight_layout(rect=[0, 0, 1, 0.94])

plt.savefig(
    "results/plots/buildheap_comparison.png",
    dpi=200,
    bbox_inches="tight"
)

plt.close()

print("Saved results/plots/buildheap_comparison.png")