#!/bin/bash

dir=/tmp/git-java
jar=codecrafters-git.jar

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
  contents="apples oranges grapes"
  echo "$contents" > contents.txt
  hash=$(git hash-object -w contents.txt)
  printf 'Created blob object with hash %s\n' "$hash"
  out=$(java -jar "$jar" "$@" "cat-file" "-p" "$hash")
  if [[ ! $out =~ $contents ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$contents" "$out"
    exit 1
  fi
  printf 'Valid blob object contents\nTest passed\n'
  popd > /dev/null
}

function test() {
  init_git
  printf '\n'
  read_blob_object
}

if [ $# -eq 0 ]; then
  test
fi

$1