pipeline {
    agent any
	
    options {
        // Timeout counter starts AFTER agent is allocated
        timeout(time: 900, unit: 'SECONDS')
    }
    
	stages {
        stage('Clean') {
            steps {
                echo 'Hello World'
				custExecGradleTask('clean')
            }
        }
        stage('Unit Tests') {
            steps {
                custExecGradleTask('test')
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
       stage('Build') {
            steps {
                echo 'Hello World'
				custExecGradleTask('build')
            }
        }	
    }
	
    post {
        always {
            echo 'I have finished'
			//mail to: 'billa.sreenivasarao@gmail.com', subject: 'Build Completed', body: 'Please verify status!'
            deleteDir() // clean up workspace
        }
        success {
            echo 'I succeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things are different...'
        }
    }
	
}

def custExecGradleTask(String... args) {
    bat "./gradlew ${args.join(' ')} -s"
}