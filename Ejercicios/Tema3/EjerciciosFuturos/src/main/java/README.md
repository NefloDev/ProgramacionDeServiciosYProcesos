# Java "Future" exercises
## [Exercise 1](Ejercicio1.java)

This Java program continuously reads URLs from the standard input and prints out the content of the corresponding web pages. It utilizes Java's CompletableFuture API for asynchronous processing.

## How the Code Works

- The `main` method serves as the entry point of the program.
- The program continuously prompts the user for input.
- Upon receiving a URL from the user, it asynchronously processes the URL to retrieve the content of the web page.
- The URL is converted to a `URL` object using `URI.create(input).toURL()`.
- The content of the web page is fetched using `url.get().openStream()` in an asynchronous manner.
- The fetched content is then read line by line and appended to a `StringBuilder` to reconstruct the page content.
- The program uses `CompletableFuture` to manage asynchronous tasks.
- Upon completion, the content of the web page is printed to the standard output.
- The program handles exceptions such as `IOException`, `ExecutionException`, and `InterruptedException` by throwing a `RuntimeException`.

## Execution Flow

1. The program prompts the user to enter a URL.
2. It asynchronously fetches the content of the web page corresponding to the entered URL.
3. Once the content is retrieved, it is printed to the standard output.
4. The program continues to prompt the user for more URLs and repeats the process indefinitely until terminated manually.

## Usage

- Compile the Java file.
- Run the compiled program.
- Enter URLs when prompted and observe the content of the corresponding web pages printed to the console.

> **Note** </br>
> This program runs indefinitely in a loop (`while(true)`), and its termination relies on manual interruption or termination of the process.
# [Exercise 2](Ejercicio2.java)

## Description

This Java program compresses the contents of a directory specified by the user (`route1`) into a ZIP file and saves the compressed file to another directory (`route2`). It utilizes Java's CompletableFuture API for asynchronous processing.

## How the Code Works

- The `main` method serves as the entry point of the program.
- It prompts the user to enter two directory paths: `route1` (source directory) and `route2` (destination directory).
- The program compresses the contents of the `route1` directory into a ZIP file named `compressed.zip`.
- Asynchronous processing is achieved using `CompletableFuture.runAsync()` to compress the directory contents.
- The `zipFile()` method is recursively called to traverse the directory structure and add files and subdirectories to the ZIP archive.
- Upon completion of compression, the `compressed.zip` file is moved to the `route2` directory using `Files.move()`.
- Exceptions like `IOException` are caught and handled gracefully.

## Execution Flow

1. The program prompts the user to enter the source and destination directories.
2. It asynchronously compresses the contents of the source directory into a ZIP file.
3. Upon completion, the compressed ZIP file is moved to the destination directory.
4. Any exceptions encountered during the process are handled gracefully.

## Usage

- Compile the Java file.
- Run the compiled program.
- Enter the source directory path (`route1`) and the destination directory path (`route2`).
- The program compresses the contents of the source directory and saves the compressed file to the destination directory.

>**Note** </br>
> Ensure the existance and permissions of the paths before executing the program
# [Exercise 3](Ejercicio3.java)

## Description

This Java program downloads the HTML content of multiple web pages asynchronously, saves the content to individual text files, and compresses them into a ZIP file. It utilizes Java's CompletableFuture API for asynchronous processing.

## How the Code Works

- The `main` method serves as the entry point of the program.
- It defines a list of web pages URLs (`webPages`) and their corresponding names (`webNames`).
- Asynchronously, the program downloads the HTML content of each web page using `CompletableFuture.supplyAsync()`.
- It iterates through each web page URL, retrieves the content, and stores it in an `ArrayList`.
- The downloaded HTML content is saved to text files named after their respective web page names.
- After all the content is downloaded and saved, the program compresses the text files into a ZIP file named `compressed.zip`.
- The program handles exceptions like `IOException` gracefully.

## Execution Flow

1. The program asynchronously downloads the HTML content of each web page.
2. Upon successful download, it saves the content to individual text files named after their respective web pages.
3. After all content is downloaded and saved, the program compresses the text files into a ZIP file.
4. The compressed ZIP file is created in the current directory.
5. Console messages indicate the progress of downloading, writing, and compressing files.

## Usage

- Compile the Java file.
- Run the compiled program.
- Wait for the program to download and save the HTML content of the specified web pages.
- Once completed, find the compressed ZIP file named `compressed.zip` in the current directory.

> **Note** </br>
> Ensure that the URLs specified in `webPages` are accessible and valid.