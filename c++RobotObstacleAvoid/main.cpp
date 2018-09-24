/*
 * main.cpp
 *
 *  Created on: Jan 18, 2017
 *      Author: user
 */

#include <HamsterAPIClientCPP/Hamster.h>
#include <iostream>
#include "Robot.h"
#include "LocalizationManager.h"
#include "Map.h"

using namespace std;
using namespace HamsterAPI;
HamsterAPI::Hamster * hamster;

int main() {
	try {
		hamster = new HamsterAPI::Hamster(1);
		sleep(3);

		OccupancyGrid ogrid = hamster->getSLAMMap();

		Map map(ogrid);
		map.initMap();
		float count = 0;
		Robot robot(hamster);
		LocalizationManager locManager(ogrid, hamster);

		locManager.initParticles();

		while (hamster->isConnected()) {
			try {
				map.showMap();
				robot.robotMovement();
				robot.updatePose();
				locManager.updateParticles(robot.getDeltaX(), robot.getDeltaY(), robot.getDeltaYaw());

					map.drawParticles(locManager.getParticles());
				//	locManager.printParticles();
				count =0;
				sleep(0.5);

			} catch (const HamsterAPI::HamsterError & message_error) {
				HamsterAPI::Log::i("Client", message_error.what());
			}

		}
	} catch (const HamsterAPI::HamsterError & connection_error) {
		HamsterAPI::Log::i("Client", connection_error.what());
	}
	return 0;
}

