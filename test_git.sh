#!/bin/bash

dir=/tmp/git-java
jar=codecrafters-git.jar

rm -rf $dir
mvn -B package -Ddir=$dir

function init_git() {
  printf 'Running test for Stage #GG4 (Initialize the .git directory)\n'
  pushd $dir > /dev/null
  rm -rf .git
  java -jar "$jar" "$@" "init"
  if [ ! -d ".git" ]; then
    printf 'Expected .git to be created but it does not exist.\nTest Failed'
    exit 1
  fi
  printf '.git directory found\n'
  if [ ! -d ".git/objects" ]; then
    printf 'Expected .git/objects to be created but it does not exist.\nTest Failed'
    exit 1
  fi
  printf '.git/objects directory found\n'
  if [ ! -d ".git/refs" ]; then
    printf 'Expected .git/refs to be created but it does not exist.\nTest Failed'
    exit 1
  fi
  printf '.git/refs directory found\n'
  if [ ! -f ".git/HEAD" ]; then
    printf 'Expected .git/HEAD to be created but it does not exist.\nTest Failed'
    exit 1
  fi
  printf '.git/HEAD file found\n'
  printf 'Test passed\n'
  popd > /dev/null
}

function read_blob_object() {
  printf 'Running test for Stage #IC4 (Read a blob object)\n'
  pushd $dir > /dev/null
  rm -rf .git
  java -jar "$jar" "$@" "init"
  fruits="apples oranges grapes"
  echo "$fruits" > fruits.txt
  hash=$(git hash-object -w fruits.txt)
  printf 'Created blob object with hash %s\n' "$hash"
  out=$(java -jar "$jar" "$@" "cat-file" "-p" "$hash")
  if [[ ! $out =~ $fruits ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$fruits" "$out"
    exit 1
  fi
  printf 'Valid blob object contents\nTest passed\n'
  popd > /dev/null
}

function create_blob_object() {
  printf 'Running test for Stage #JT4 (Create a blob object)\n'
  pushd $dir > /dev/null
  if [ ! -d ".git" ]; then
    java -jar "$jar" "$@" "init"
  fi
  fruits="apples oranges grapes"
  echo "$fruits" > fruits.txt
  hash=$(git hash-object -w fruits.txt)
  rm -rf .git
  java -jar "$jar" "$@" "init"
  out=$(java -jar "$jar" "$@" "hash-object" "-w" "fruits.txt")
  if [[ ! $out =~ $hash ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$hash" "$out"
    exit 1
  fi
  printf 'Valid hash %s\nTest passed\n' "$hash"
  popd > /dev/null
}

function read_tree_object() {
  printf 'Running test for Stage #KP1 (Read a tree object)\n'
  pushd $dir > /dev/null
  if [ ! -d ".git" ]; then
    java -jar "$jar" "$@" "init"
  fi
  fruits="apples oranges grapes"
  mkdir "fruits"
  echo "$fruits" > fruits/fruits.txt
  git add fruits
  hash=$(git write-tree)
  out=$(java -jar "$jar" "$@" "ls-tree" "--name-only" "$hash")
  if [[ ! $out =~ fruits ]] ; then
    printf 'Expected fruits, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf 'Valid output fruits\nTest passed\n'
  popd > /dev/null
}

function write_tree_object() {
  printf 'Running test for Stage #FE4 (Write a tree object)\n'
  pushd $dir > /dev/null
  if [ -d ".git" ]; then
    rm -rf ".git"
  fi
  java -jar "$jar" "$@" "init"
  if [ -d "fruits" ]; then
    rm -r "fruits"
    rm fruits.txt
  fi
  mkdir "fruits"
  echo "apples oranges grapes" > fruits/fruits.txt
  hash=$(java -jar "$jar" "$@" "write-tree")
  out=$(java -jar "$jar" "$@" "ls-tree" "--name-only" "$hash" | tail -n1)
  if [[ ! $out =~ fruits ]] ; then
    printf 'Expected fruits, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf 'Valid output fruits\nTest passed\n'
  popd > /dev/null
}

function create_commit() {
  printf 'Running test for Stage #JM9 (Create a commit)\n'
  pushd $dir > /dev/null
  if [ -d ".git" ]; then
    rm -rf ".git"
  fi
  java -jar "$jar" "$@" "init"
  if [ -d "fruits" ]; then
    rm -r "fruits"
  fi
  mkdir "fruits"
  echo "apples oranges grapes" > fruits/fruits.txt
  tree_hash=$(git write-tree)
  echo "pear apple orange pineapple" > fruits/salad.txt
  git add fruits
  git commit -m "Initial commit"
  commit_hash=$(git rev-parse HEAD)
  message="Fruits for a fruit salad"
  out=$(java -jar "$jar" "$@" "commit-tree" "$tree_hash" "-p" "$commit_hash" "-m" "$message")
  out=$(git rev-list --format=%B --max-count=1 $out | head -2 | tail -1)
  if [[ "$out" != "$message"  ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$message" "$out"
    exit 1
  fi
  printf 'Valid commit message\nTest passed\n'
  popd > /dev/null
}

function test() {
  init_git
  printf '\n'
  read_blob_object
  printf '\n'
  create_blob_object
  printf '\n'
  read_tree_object
  printf '\n'
  write_tree_object
  printf '\n'
  create_commit
}

if [ $# -eq 0 ]; then
  test
fi

$1