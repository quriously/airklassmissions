#!/bin/sh

echo "#############################"
echo "#### remove docker image ####"
echo "#############################"
docker rmi $(docker images -q)

echo "##################################"
echo "###### git checkout release ######"
echo "##################################"
git checkout master

echo "###########################"
echo "######### git pull ########"
echo "###########################"

git pull origin main

echo "##############################################"
echo "#### Grant execute permission for gradlew ####"
echo "##############################################"
chmod +x gradlew

#echo "#######################################"
#echo "######### upgrade jar version #########"
#echo "#######################################"
# 서버 인스턴스 스펙이 너무 낮아 중간에 자꾸 뻑나서 일일이 수작업으로 빌드 돌려줘야 할듯
# 또는 code deploy 또는 s3에 jar파일 올려놓고 jar파일만 받아서 진행 또는 인스턴스 스펙을 올리던지.....
# 가상 메모리를 이용하여 완료
./gradlew clean build

#echo "#######################################"
#echo "######### select application env ######"
#echo "#######################################"

#java -jar giftfunding-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

echo "##################################"
echo "######### docker compose #########"
echo "##################################"
docker-compose up --build -d --force-recreate