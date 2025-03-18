# Perintah Dasar Docker

## ✅ Install Docker

Install Docker bisa menggunakan 2 cara:
1. Install Docker Desktop: https://docs.docker.com/get-started/get-docker/
2. Install Docker Engine di Linux atau WSL: https://docs.docker.com/engine/install/ 

## ✅ Cek Versi

> Docker memerlukan akses administrator untuk eksekusi perintah `docker` sistem Linux. Agar bisa digunakan tanpa akses administrator, masukan user ke dalam group docker dengan perintah `sudo usermod -aG docker $USER`

```
docker version
```

## ✅ Docker Command Help

```
docker --help
```

atau

```
docker -h
```

atau untuk command spesifik

```
docker image --help
```

## 1️⃣ Docker Images

### Melihat Daftar Docker Image

```
docker image ls
```

### Download Image Docker

Cari docker image di https://hub.docker.com atau search dengan perintah:

```
docker search <nama_image>
```

Download / pull image

```
docker image pull <name_space>/<nama_image>:<tag>
```

> `<name_space>` biasanya sama dengan username dari pembuat image, berupa alamat kumpulan dari image-image yang dipublish oleh pembuaat image tersebut. Jika melakukan pulling dari official image maka tidak perlu menyertakan `<name_space>`.

Contoh pull image database MariaDB

```
docker pull mariadb:latest
```

> Secara default docker akan melakukan pull dari repository [docker hub](https://hub.docker.com/) milik Docker. Kita dapat mengganti reposity tersebut dengan repository lain dengan perintah lengkap `docker image pull  <DockerRegistry>/<namespace>/<image>:<tag>`

### Menghapus Image

```
docker image rm <nama_image>:<tag>
```

## 2️⃣ Docker Container

Container adalah instance dari image. Kita bisa membuat lebih dari satu container dari image yang sama. Container tersebut saling terisolasi sehingga tidak akan bentrok aplikasinya.

![Docker Container Lifecycle](https://blog.techiescamp.com/content/images/2024/05/dockerlifecycle-2.gif)

### Melihat semua Container baik yang running atau stoped

```
docker container ls -a
```

### Melihat semua Container yang running saja

```
docker container ls
```

atau

```
docker ps
```

### Membuat Container

```
docker container create --name <nama_container> <nama_image>:<tag>
```

Contoh:

```
docker container create --name contohhttpd httpd:latest
```

### Menjalankan Container

```
docker container start contohhttpd
```

atau kita bisa membuat container sekaligus menjalankannya. Contoh menjalankan container `nginx:stable`:

```
docker container run -p 8080:80 nginx:stable
```

Contoh mennjalankan container `nginx:stable` baru dengan beberapa properti (cek properti dengan perintah `--help`):

```
docker run -d -p 8081:80 --name=nginx2 nginx:stable
```

### Menghentikan Container

```
docker container stop contohhttpd
```

atau

```
docker container stop <container_id>
```

atau

```
docker container kill <container_id>
```

> Perbedaan `kill` dan `stop`
> * `docker container stop`
>   * sinyal yang dikirim adalah SIGTERM yang dimana container akan melakukan shutdown dan melakukan terminate semua prosesnya secara normal.
>   * Jika dalam 10 detik container tersebut belum mati, maka sinyal yang akan dikirim adalah SIGKILL, yang akan memaksa container mati seketika itu juga.
> * `docker container kill`
>   * Sinyal yang dikirim langsung SIGKILL, yang artinya container akan dipaksa mati seketika itu juga.

### Menghapus Container

```
docker container rm contohhttpd
```

atau

```
docker container rm <container_id>
```

> Untuk menghapus container harus dalam keadaan stop. Jika ingin menghapus container dalam keadaan running gunakan perintah 

### Melihat Log

```
docker container logs contohtomcat
```

Melihat Log Realtime

```
docker container logs -f contohtomcat
```

### Docker Container Attach

Fungsi Docker container attach adalah melampirkan input, output, dan error standar terminal ke kontainer yang sedang berjalan. Dengan begitu, Anda bisa melihat output atau mengendalikan kontainer secara interaktif. 

```
docker container attach <nama_container>
```

Dengan melakukan attach kita bisa langsung melihat log container secara realtime.

### Docker Container Exec

Docker Container Exec digunakan untuk menjalankan perintah pada container yang sedang berjalan.

Contoh menjalankan perintah `ls` untuk melihat file dan folder dalam container `nginx`

```
docker container exec <nama_container> ls
```

Contoh lain menggunakan option `-i` `-t` (baca `--help`) masuk ke dalam perintah `bash` container:

```
docker container  exec  -it <nama_container> bash
```

### Copy file ke dalam Container

Contoh kita akan copy file dari local ke dalam container dengan path `/usr/share/nginx/html/`

```
docker container cp <nama_file> <nama_container>:/usr/share/nginx/html/
```

## 3️⃣ Docker File

### Membuat Docker File

Dockerfile adalah file teks yang berisi instruksi untuk membuat objek kontainer. File docker file bernama `Dockerfile`. Contoh isi docker file:

```docker
FROM ubuntu:24.04
RUN apt-get update && apt-get install curl -y
CMD ["/usr/bin/curl", "http://timposulabs.com"]
```

Keterangan: 

1. Menjalankan/pull image `ubuntu:24.04`
2. Menjalankan `apt-get update && apt-get install curl -y` 
3. Mejalankan perintah `/usr/bin/curl http://timposulabs.com`

## 4️⃣ Docker Build

* Cek help

```
docker image build --help
```


* Build

Membuat build dengan berdasarkan user yang ada di [docker hub](https://hub.docker.com/) dengan nama image `first-image` pada direktory yang sama.

```
docker image build -t topekox/first-image:1.0.0 .
```

* Cek docker image

```
docker image ls
```

* Jalankan

```
docker container run topekox/first-image:1.0.0
```

## Push Image ke Repository

### Login

```
docker login
```

### Push Image

Help

```
docker push --help
```

atau

```
docker image push --help
```

Push

```
docker image push topekox/first-image:1.0.0
```
