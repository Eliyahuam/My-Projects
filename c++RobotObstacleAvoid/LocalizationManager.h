/*
 * LocalizationManager.h
 *
 *  Created on: Jan 11, 2017
 *      Author: user
 */

#ifndef LOCALIZATIONMANAGER_H_
#define LOCALIZATIONMANAGER_H_

#include "Particle.h"
#include <vector>
#include <HamsterAPIClientCPP/Hamster.h>

using namespace std;
using namespace HamsterAPI;

class LocalizationManager {
private:
	vector<Particle *> particles;
	const OccupancyGrid &ogrid;
	Hamster *hamster;
	void getRandomLocation(Particle *particle);
	double computeBelief(Particle *particle);

public:
	void getParticleIfCellNotFree(Particle *particle);
	//void roulette_wheel(int fitness [population], char chromosome[population][chromelength], char parent[population][chromelength])
	int rouletteWheel();
	void getRandomNearGoodParticles();
	void sortByBelief();
	LocalizationManager(const OccupancyGrid &ogrid, Hamster *hamster);
	void initParticles();
	void updateParticles(double deltaX, double deltaY, double deltaYaw);
	void printParticles() const;
	vector<Particle *> getParticles() const;

	virtual ~LocalizationManager();
};

#endif /* LOCALIZATIONMANAGER_H_ */
