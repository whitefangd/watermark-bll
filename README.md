# watermark-bll

## Command line run demo

- Encode

`java -jar watermark.jar -E -al LSB -c <image path>.bmp -sc <file path> -st <image path>.bmp`

- decode

`java -jar watermark.jar -D -al LSB -st <image path>.bmp -o <file path>`

> Stego file (-st) only support bitmap image
