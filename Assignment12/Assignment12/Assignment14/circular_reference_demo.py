import sys
import gc

class Node:
    def __init__(self, name):
        self.name = name
        self.link = None
    
    def __del__(self):
        print(f"[DESTRUCTOR] Node '{self.name}' is being destroyed")
    
    def __repr__(self):
        return f"Node(name='{self.name}')"


def demonstrate_circular_reference():
    print("=" * 80)
    print("ASSIGNMENT 14: CIRCULAR REFERENCES AND GARBAGE COLLECTION")
    print("=" * 80)
    print()
    
    print("STEP 1: CREATE NODES")
    print("-" * 80)
    node_a = Node("A")
    node_b = Node("B")
    print(f"Created: {node_a}")
    print(f"Created: {node_b}")
    print()
    
    print("STEP 2: CHECK INITIAL REFERENCE COUNTS")
    print("-" * 80)
    ref_count_a_before = sys.getrefcount(node_a)
    ref_count_b_before = sys.getrefcount(node_b)
    print(f"Reference count for Node A: {ref_count_a_before}")
    print(f"  (includes: local variable, getrefcount parameter, temporary reference)")
    print(f"Reference count for Node B: {ref_count_b_before}")
    print(f"  (includes: local variable, getrefcount parameter, temporary reference)")
    print()
    
    print("STEP 3: CREATE CIRCULAR REFERENCE")
    print("-" * 80)
    node_a.link = node_b
    node_b.link = node_a
    print(f"Set A.link = B")
    print(f"Set B.link = A")
    print(f"Circular reference created: A -> B -> A")
    print()
    
    print("STEP 4: CHECK REFERENCE COUNTS AFTER CIRCULAR REFERENCE")
    print("-" * 80)
    ref_count_a_after = sys.getrefcount(node_a)
    ref_count_b_after = sys.getrefcount(node_b)
    print(f"Reference count for Node A: {ref_count_a_after}")
    print(f"  (increased by 1 due to B.link pointing to A)")
    print(f"Reference count for Node B: {ref_count_b_after}")
    print(f"  (increased by 1 due to A.link pointing to B)")
    print()
    
    print("STEP 5: DISABLE AUTOMATIC GARBAGE COLLECTION")
    print("-" * 80)
    gc.disable()
    print("Garbage collection disabled to demonstrate the problem")
    print()
    
    print("STEP 6: DELETE REFERENCES")
    print("-" * 80)
    print("Executing: del node_a")
    del node_a
    print("Executing: del node_b")
    del node_b
    print("Both variables deleted from local scope")
    print()
    
    print("STEP 7: INVESTIGATE UNREACHABLE OBJECTS")
    print("-" * 80)
    print("Checking garbage collector for unreachable objects...")
    gc.collect()
    
    unreachable_objects = gc.garbage
    print(f"Number of objects in gc.garbage: {len(unreachable_objects)}")
    print()
    
    print("STEP 8: FIND CIRCULAR REFERENCES IN GARBAGE")
    print("-" * 80)
    node_objects = [obj for obj in gc.garbage if isinstance(obj, Node)]
    print(f"Found {len(node_objects)} Node objects in garbage")
    
    if node_objects:
        for i, node in enumerate(node_objects, 1):
            print(f"\n  Node {i}: {node}")
            print(f"    - name: {node.name}")
            print(f"    - link: {node.link}")
            if node.link:
                print(f"    - link.name: {node.link.name}")
                print(f"    - link.link: {node.link.link}")
    print()
    
    print("STEP 9: ENABLE GARBAGE COLLECTION AND CLEANUP")
    print("-" * 80)
    gc.enable()
    print("Garbage collection re-enabled")
    print()
    
    print("STEP 10: FORCE GARBAGE COLLECTION")
    print("-" * 80)
    print("Calling gc.collect() to force garbage collection...")
    collected = gc.collect()
    print(f"Number of unreachable objects collected: {collected}")
    print()
    
    print("STEP 11: VERIFY CLEANUP")
    print("-" * 80)
    remaining_garbage = len(gc.garbage)
    print(f"Objects remaining in gc.garbage: {remaining_garbage}")
    print()


def demonstrate_with_debug_info():
    print("=" * 80)
    print("DETAILED ANALYSIS: CIRCULAR REFERENCE LIFECYCLE")
    print("=" * 80)
    print()
    
    print("PHASE 1: OBJECT CREATION")
    print("-" * 80)
    gc.disable()
    
    node_a = Node("X")
    node_b = Node("Y")
    
    print(f"Created Node X: id={id(node_a)}, refcount={sys.getrefcount(node_a)}")
    print(f"Created Node Y: id={id(node_b)}, refcount={sys.getrefcount(node_b)}")
    print()
    
    print("PHASE 2: CREATE CYCLE")
    print("-" * 80)
    node_a.link = node_b
    node_b.link = node_a
    
    print(f"After creating cycle:")
    print(f"  Node X: refcount={sys.getrefcount(node_a)}")
    print(f"  Node Y: refcount={sys.getrefcount(node_b)}")
    print()
    
    print("PHASE 3: DELETE VARIABLES (OBJECTS BECOME UNREACHABLE)")
    print("-" * 80)
    print("Before deletion:")
    print(f"  Node X is accessible: {node_a is not None}")
    print(f"  Node Y is accessible: {node_b is not None}")
    print()
    
    del node_a
    del node_b
    
    print("After deletion:")
    print("  Node X is no longer accessible from code")
    print("  Node Y is no longer accessible from code")
    print("  BUT: They still exist in memory due to circular reference!")
    print()
    
    print("PHASE 4: INVESTIGATE GARBAGE")
    print("-" * 80)
    print("Calling gc.collect() to identify unreachable objects...")
    unreachable_count = gc.collect()
    print(f"Unreachable objects found: {unreachable_count}")
    print()
    
    print("PHASE 5: EXAMINE GARBAGE COLLECTOR STATE")
    print("-" * 80)
    gc_objects = gc.get_objects()
    node_count = sum(1 for obj in gc_objects if isinstance(obj, Node))
    print(f"Total objects in memory: {len(gc_objects)}")
    print(f"Node objects in memory: {node_count}")
    print()
    
    print("PHASE 6: CLEANUP")
    print("-" * 80)
    gc.enable()
    gc.collect()
    print("Garbage collection enabled and cleanup performed")
    print()


def demonstrate_reference_cycle_types():
    print("=" * 80)
    print("REFERENCE CYCLE TYPES AND DETECTION")
    print("=" * 80)
    print()
    
    print("TYPE 1: SIMPLE CYCLE (A -> B -> A)")
    print("-" * 80)
    gc.disable()
    
    a = Node("A1")
    b = Node("B1")
    a.link = b
    b.link = a
    
    print(f"Created cycle: A1 -> B1 -> A1")
    print(f"A1 refcount: {sys.getrefcount(a)}")
    print(f"B1 refcount: {sys.getrefcount(b)}")
    
    del a
    del b
    
    collected = gc.collect()
    print(f"Objects collected: {collected}")
    print()
    
    print("TYPE 2: SELF-REFERENCE (A -> A)")
    print("-" * 80)
    
    c = Node("C")
    c.link = c
    
    print(f"Created self-reference: C -> C")
    print(f"C refcount: {sys.getrefcount(c)}")
    
    del c
    
    collected = gc.collect()
    print(f"Objects collected: {collected}")
    print()
    
    print("TYPE 3: COMPLEX CYCLE (A -> B -> C -> A)")
    print("-" * 80)
    
    d = Node("D")
    e = Node("E")
    f = Node("F")
    
    d.link = e
    e.link = f
    f.link = d
    
    print(f"Created cycle: D -> E -> F -> D")
    print(f"D refcount: {sys.getrefcount(d)}")
    print(f"E refcount: {sys.getrefcount(e)}")
    print(f"F refcount: {sys.getrefcount(f)}")
    
    del d
    del e
    del f
    
    collected = gc.collect()
    print(f"Objects collected: {collected}")
    print()
    
    gc.enable()


def demonstrate_memory_impact():
    print("=" * 80)
    print("MEMORY IMPACT: WITH AND WITHOUT GARBAGE COLLECTION")
    print("=" * 80)
    print()
    
    print("SCENARIO 1: WITHOUT GARBAGE COLLECTION")
    print("-" * 80)
    gc.disable()
    
    nodes_before = len(gc.get_objects())
    print(f"Objects in memory before: {nodes_before}")
    
    for i in range(100):
        a = Node(f"A{i}")
        b = Node(f"B{i}")
        a.link = b
        b.link = a
        del a
        del b
    
    nodes_after = len(gc.get_objects())
    print(f"Objects in memory after creating 200 circular nodes: {nodes_after}")
    print(f"Memory leak: {nodes_after - nodes_before} objects")
    print()
    
    print("SCENARIO 2: WITH GARBAGE COLLECTION")
    print("-" * 80)
    
    collected = gc.collect()
    print(f"gc.collect() called, collected: {collected} objects")
    
    nodes_after_gc = len(gc.get_objects())
    print(f"Objects in memory after gc.collect(): {nodes_after_gc}")
    print(f"Freed: {nodes_after - nodes_after_gc} objects")
    print()
    
    gc.enable()


if __name__ == "__main__":
    demonstrate_circular_reference()
    print("\n")
    demonstrate_with_debug_info()
    print("\n")
    demonstrate_reference_cycle_types()
    print("\n")
    demonstrate_memory_impact()
    
    print("=" * 80)
    print("ASSIGNMENT 14 COMPLETE")
    print("=" * 80)
