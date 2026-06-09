from functools import reduce

def total_time_per_user(logs: list[dict]) -> dict[str, float]:
    def accumulate(acc: dict[str, float], log: dict) -> dict[str, float]:
        acc[log["user"]] = acc.get(log["user"], 0.0) + log["duration"]
        return acc
    return reduce(accumulate, logs, {})

def most_active_users(logs: list[dict], k: int) -> list[str]:
    user_times = total_time_per_user(logs)
    return sorted(user_times.keys(), key=lambda u: user_times[u], reverse=True)[:k]

def unique_actions(logs: list[dict]) -> set[str]:
    return {log["action"] for log in logs}

if __name__ == "__main__":
    records = [
        {"user": "CSB001", "action": "VS Code", "duration": 2.5},
        {"user": "CSB002", "action": "GitHub", "duration": 1.2},
        {"user": "CSB001", "action": "Browser", "duration": 0.8},
        {"user": "CSB003", "action": "VS Code", "duration": 4.0},
        {"user": "CSB002", "action": "VS Code", "duration": 3.0},
        {"user": "CSB001", "action": "GitHub", "duration": 1.5}
    ]

    print("Total Time Per User:")
    times = total_time_per_user(records)
    for user, time_spent in times.items():
        print(f"- {user}: {time_spent} hours")

    print("\nMost Active Users (Top 2):")
    top_users = most_active_users(records, 2)
    for user in top_users:
        print(f"- {user}")

    print("\nUnique Actions:")
    actions = unique_actions(records)
    for action in actions:
        print(f"- {action}")

    print("\nComplexity Analysis:")
    print("a. Time complexity for computing top K users: O(N + U * log U)")
    print("   Where N is the total number of logs and U is the number of unique users.")
    print("   Total time aggregation takes O(N), and sorting unique users takes O(U * log U).")
    print("b. Space complexity of storing intermediate results: O(U)")
    print("   To store the accumulated times per user and the unique list of users for sorting.")
