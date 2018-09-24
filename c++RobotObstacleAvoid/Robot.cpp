/*
 * Robot.cpp
 *
 *  Created on: Jan 18, 2017
 *      Author: user
 */

#include "Robot.h"
using namespace std;

Robot::Robot(Hamster *hamster) :hamster(hamster), prevX(0), prevY(0), prevYaw(0), currX(0), currY(0), currYaw(0) {
	// TODO Auto-generated constructor stub
	updatePose();
}

double Robot::getDeltaX() const {
	return currX - prevX;
}

double Robot::getDeltaY() const {
	return currY - prevY;
}

double Robot::getDeltaYaw() const {
	return currYaw - prevYaw;
}

void Robot::robotMovement() {

		LidarScan ld = hamster->getLidarScan();

		if (ld.getDistance(180) < 0.4) {
			hamster->sendSpeed(-0.5, 0);
			//cout << "Front: " << ld.getDistance(180) << endl;
		} else if (ld.getDistance(180) < 0.8) {
			hamster->sendSpeed(0.5, 45);
			//cout << "Front: " << ld.getDistance(180) << endl;
		}

		else
			hamster->sendSpeed(1.0, 0);

}
void Robot::updatePose() {
	Pose pose = hamster->getPose();

	prevX = currX;
	prevY = currY;
	prevYaw = currYaw;

	currX = pose.getX();
	currY = pose.getY();
	currYaw = pose.getHeading();
}

Robot::~Robot() {
	// TODO Auto-generated destructor stub
}

