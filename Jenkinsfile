pipeline {
    agent any
	
    options {
        // Timeout counter starts AFTER agent is allocated
        timeout(time: 900, unit: 'SECONDS')
		disableConcurrentBuilds(abortPrevious: true)
		timestamps()
    }
    
	stages {
        stage('Clean') {
            steps {
                echo 'Execute gradle clean step'
				custExecGradleTask('clean')
            }
        }
        stage('Unit Tests') {
		   
            steps {
    			echo 'Execute gradle test step'
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
                echo 'Execute gradle build step'
				custExecGradleTask('build')
            }
        }	
       stage('Assemble') {
            steps {
			    echo 'Execute gradle assemble step'
                custExecGradleTask('assemble')
                stash includes: '**/build/libs/*.war', name: 'app'
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