#!/usr/bin/bash

# 切换application or  lib
result=$(cat ../$1/build.gradle)

echo "$result"

temp=${result//com.android.library/com.android.application}

temp=${temp//$2/$3}

echo "${temp}" >../$1/build.gradle




