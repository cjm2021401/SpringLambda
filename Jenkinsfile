node {
    stage('Clone') {
        checkout scm
    }

    stage('Build') {
        sh 'docker build -t demo .'
    }

    stage('Upload') {
        sh 'aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 592247757306.dkr.ecr.ap-northeast-2.amazonaws.com'
        sh 'docker tag demo:latest 592247757306.dkr.ecr.ap-northeast-2.amazonaws.com/demo:latest'
        sh 'docker push 592247757306.dkr.ecr.ap-northeast-2.amazonaws.com/demo:latest'

    }
    stage('Rollout'){
        sh 'kubectl rollout restart deploy demo-deployment -n demo'
    }
    stage('Delete'){
        sh 'docker image prune -f'
    }
    stage('Clean'){
            cleanWs()
       }

}