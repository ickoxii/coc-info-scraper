# CoC Info Scraper

This is my first introduction into using API's. I am currently taking a Software
Engineering class in Java and figure this will also be a good way to learn that
as well.

## Usage

Your api-token should be stored in `src/main/resources/api-token`

Account/clan tags are located in `src/main/java/io/github/ickoxii/core/CocInfoScraper.java`.

```zsh
# build the project
make build

# run the project
make run
```

## Description

This program gets clans associated with my accounts, then makes leaderboards for
each of these. These include current stat-lines such as player with the highest
trophies, most donations this season, etc. It also makes leaderboards for
achievements, such as Gold Grab or Friend in Need.

## TODO

1. Do analysis of achievements across all players, i.e. median, mean, mode, quartiles, sd

2. Standardize stat analysis for achievements, current season stats and exp levels
