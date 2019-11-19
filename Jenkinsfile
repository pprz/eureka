pipeline {
  agent none
  stages {
    stage('maven Build') {
      agent {
        docker {
          image 'maven:3-alpine'
          args '-v /root/.m2:/root/.m2'
        }
      }
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('docker build & push') {
      agent any
      steps {
        script {
          def REMOVE_FLAG = sh(returnStdout: true, script: "docker image ls -q *liker163/eureka-testver*") != ""
          echo "REMOVE_FLAG: ${REMOVE_FLAG}"
          if(REMOVE_FLAG){
            sh 'docker image rm -f $(docker image ls -q *liker163/eureka-testver*)'
          }
        }

        withCredentials([usernamePassword(credentialsId: 'liker163ID', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh 'docker login -u $USERNAME -p $PASSWORD'
          sh 'docker image build -t liker163/eureka-testver .'
          sh 'docker push liker163/eureka-testver'
        }
      }
    }

    stage('docker pull image and docker run image') {
      agent any
      steps {
        script {
          def REMOVE_FLAG = sh(returnStdout: true, script: "docker container ls -a -q --filter name=eurekacontain") != ""
          echo "REMOVE_FLAG: ${REMOVE_FLAG}"
          if(REMOVE_FLAG){
            sh 'docker container rm -f $(docker container ls -a -q --filter name=eurekacontain)'
          }
        }

        script {
          def REMOVE_FLAG = sh(returnStdout: true, script: "docker image ls -q *liker163/eureka-testver*") != ""
          echo "REMOVE_FLAG: ${REMOVE_FLAG}"
          if(REMOVE_FLAG){
            sh 'docker image rm -f $(docker image ls -q *liker163/eureka-testver*)'
          }
        }

        // docker pull image from docker hub registry
        sh 'docker pull liker163/eureka-testver'

        // docker run images
        sh 'docker run -d -p 8762:8761 -v fsdms-data:/fsdms-data --network app-net --name eurekacontain liker163/eureka-testver'
      }
    }

    stage('clean workspace') {
      agent any
      steps {
        // clean workspace after job finished
        cleanWs()
      }
    }
  }
}


