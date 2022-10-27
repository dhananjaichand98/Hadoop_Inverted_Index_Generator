<a name="readme-top"></a>
# Hadoop Inverted Index Generator

## Description

An inverted index is an index data structure storing a mapping from words to their documents(location) along with the frequency of occurrence. This repository contains two Programs that generate inverted indexes for a large corpus of documents using Hadoop. ```InvertedIndexJob.java``` is an implementation of an inverted index for unigrams using Hadoop MapReduce. ```BigramInvertedIndexJob.java``` is an implementation of an inverted index for bigrams using Hadoop MapReduce. GCP Dataproc clusters are used to run MapReduce jobs and generate inverted indexes.

## Getting Started

### Dependencies

* Java
* Apache Hadoop

### Installing

Use the following steps for installation.

1. Clone the repo
   ```sh
   git clone https://github.com/dhananjaichand98/Hadoop_Inverted_Index_Generator.git
   ```

### Executing program

These two java programs are uploaded as jar files to a GCP Dataproc cluster and MapReduce jobs are run on a corpus taken from the following link: https://drive.google.com/drive/u/1/folders/1Z4KyalIuddPGVkIm6dUjkpD_FiXyNIcq.

## Data
The program uses a subset of 74 files from a total of 408 files (text extracted from HTML tags) derived from the Stanford WebBase project that is available [here](https://ebiquity.umbc.edu/resource/html/id/351). It was obtained from a web crawl done in February 2007. It is one of the largest collections totaling more than 100 million web pages from more than 50,000 websites.

## Authors

- [Dhananjai Chand](https://www.linkedin.com/in/dhananjai-chand/)

## Acknowledgment

* [WordCount v1.0](https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
