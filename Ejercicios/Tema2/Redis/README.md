# Redis

This program is designed to shorten urls, using redis to store them  by their ending in a custom domain.


# How does it work

When an url is entered the program stores it into a hash set of urls to shorten, then a second program that is running infinitely gets the url that was entered first (like a FIFO list) to generate a random ending for it and setting it with the custom domain using this format: **short.net/XXXXXXXXX** being the X the random ending.

## How to use the program

To use this program you have to execute it, whether it is from a command line or an IDE the first message that comes up is a command guide, about which commands are available and how to use them.

## Available Commands
```
help : shows the command list
shorten <url to shorten> : returns the shorted url for the given full url
url <shortened url> : returns the full url for the given shorted url
exit : finishes executing the program
```

## Author
[NefloArt](https://github.com/NefloArt)
## Teacher
[nafarrin](https://github.com/nafarrin)
