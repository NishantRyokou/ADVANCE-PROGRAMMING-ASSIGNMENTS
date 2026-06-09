import sys
import gc

class Node:
    def __init__(self, name):
        self.name = name
        self.link = None

    def __repr__(self):
        return f"Node({self.name})"

def main():
    gc.disable()

    a = Node("A")
    b = Node("B")

    a.link = b
    b.link = a

    print(sys.getrefcount(a))
    print(sys.getrefcount(b))

    id_a = id(a)
    id_b = id(b)

    del a
    del b

    orphaned = [obj for obj in gc.get_objects() if id(obj) in (id_a, id_b)]
    print(len(orphaned))

    del orphaned

    collected = gc.collect()
    print(collected)

    gc.enable()

if __name__ == "__main__":
    main()
