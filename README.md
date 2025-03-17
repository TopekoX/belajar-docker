# Perintah Dasar Docker

## Install Docker

Install Docker bisa menggunakan 2 cara:
1. Install Docker Desktop: https://docs.docker.com/get-started/get-docker/
2. Install Docker Engine di Linux atau WSL: https://docs.docker.com/engine/install/ 

## Cek Versi

> Docker memerlukan akses administrator untuk eksekusi perintah `docker` sistem Linux. Agar bisa digunakan tanpa akses administrator, masukan user ke dalam group docker dengan perintah `sudo usermod -aG docker $USER`

```
docker version
```

## Docker Command Help

```
docker --help
```

atau untuk command spesifik

```
docker image --help
```

## Docker Images

### Melihat Docker Image

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

## Docker Container

### Melihat semua Container baik yang running atau stoped

```
docker container ls -a
```

### Melihat semua Container baik yang running saja

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

Kita bisa membuat lebih dari satu container dari image yang sama. Container tersebut saling terisolasi sehingga tidak akan bentrok aplikasinya.


### Menghentikan Container

```
docker container stop contohhttpd
```

### Menghapus Container

```
docker container rm contohhttpd
```

### Melihat Log

```
docker container logs contohtomcat
```

Melihat Log Realtime

```
docker container logs -f contohtomcat
```

## Docker File

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

## Docker Build

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
