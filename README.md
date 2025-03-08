# CrawlMaster

High-performance web crawler built in Java

## Installation



```bash
git clone https://github.com/KacperNowakCode/CrawlMaster
cd CrawlMaster
cd crawler
```
Ensure you have Maven installed. To download and resolve dependencies, run:

```bash
mvn clean install
mvn clean package
```

## Usage

**For Windows:**  
```bash
echo link-here | java -jar target/Crawler-1.0.jar
```
If you want to crawl multiple links at once:
1. Paste links into urls.txt file. Then:

```bash
Get-Content urls.txt | java -jar target/Crawler-1.0.jar
```

**For Linux:**  
```bash
echo link-here | java -jar target/Crawler-1.0.jar
```
If you want to crawl multiple links at once:
1. Paste links into urls.txt file. Then:

```bash
cat urls.txt | java -jar ./target/Crawler-1.0.jar
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
