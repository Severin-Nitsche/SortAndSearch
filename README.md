# SortAndSearch
Sort and Search algorithms

## How to use
If you're on a **Mac** or **Linux** System:<br />
_Step 1:_<br />
Grand the ```compile.cmd``` and the ```build.cmd``` execution rights using:<br />
```chmod 777 compile.cmd``` and ```chmod 777 build.cmd```<br />
_Step 2:_<br />
execute the ```build.cmd``` using:<br />
```./build.cmd```<br />
_Step 3:_<br />
Import the generated jar file in the build directory in your other Projects and enjoy!

If you're using **Windows** or **Anything**:<br />
_Step 1:_ Compile the Java source file<br />
_Step 2:_ Create a jar file<br />
_Step 3:_ Enjoy!

## Javadoc
[Javadoc](https://severin-nitsche.github.io/SortAndSearch/SortAndSearch/doc)

## Algorithms

### Sorting Algorithms:

<details>
  <summary>Bubble Sort</summary>
  <p>Bubble Sort works by going through a list and comparing adjacent elements and swapping them if unsorted. This procedure is repeated until the list is fully sorted.</p>
</details>
<details>
  <summary>Binary Tree Sort</summary>
  <p>Binary Tree Sort works by going through a list and inserting its elements into a tree structure, which, branching out from one root node, consists of several nodes that, besides one value, each keep two references: One to a node with a higher value, and anotherone to a node with a lower value. If one imagines the higher value references as branching out right and the lower value references as branching out left, themost right node in the newly populated tree includes the highest and the most left the lowest value. Knowing this its a breeze to desolve the tree and obtain a sorted list.</p>
</details>
<details>
  <summary>Quick Sort</summary>
  <p>Quick Sort works by choosing a pivot Element from the unsorted list and then creating two lists, one with all values that are lower than the pivot and another one with all values that are higher than the pivot. Quick Sort is then used on this lists again and the result of them is then concatenated to each other with the pivot in the middle. This recursive behavior will only be terminated when the list given to Quick Sort is fully sorted.</p>
</details>
<details>
  <summary>Radix Sort MSD</summary>
</details>
<details>
  <summary>Radix Sort LSD</summary>
</details>

### Searching Algorithms:

Linear Search<br />
Binary Tree Search<br />

## Data Structures

[Linked List](https://severin-nitsche.github.io/SortAndSearch/SortAndSearch/doc/severinnitsche/com/github/SortAndSearch.LinkedList.html)<br />
[Binary Tree](https://severin-nitsche.github.io/SortAndSearch/SortAndSearch/doc/severinnitsche/com/github/SortAndSearch.BinaryTree.html)<br />
