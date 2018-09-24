/*
 * LocalizationManager.cpp
 *
 *  Created on: Jan 11, 2017
 *      Author: user
 */

#include "LocalizationManager.h"
#include <iostream>
#include <cmath>

using namespace std;
#define PARTICLES_NUM 500
#define DEL_PARTICLE 200
#define RADIUS 8
#define ROULETTE 5
#define TRASHOLD 0.5



LocalizationManager::LocalizationManager(const OccupancyGrid &ogrid, Hamster *hamster) : ogrid(ogrid), hamster(hamster) {
	// TODO Auto-generated constructor stub
	cout << "width: " << ogrid.getWidth() << ", height: " << ogrid.getHeight() << endl;
}

void LocalizationManager::getRandomNearGoodParticles() {

	sortByBelief();
	int j=0;

	for (int i = PARTICLES_NUM-1 ;i > PARTICLES_NUM -1 - DEL_PARTICLE ; i--) {
		int roulette = rouletteWheel();
		do{

			int angle = rand() % 360;
			int radius = rand() % RADIUS;
			particles[j]->j = (particles[roulette]->j + radius * cos (angle));
			particles[j]->i = (particles[roulette]->i + radius * sin (angle));

		} while (ogrid.getCell(particles[j]->i, particles[j]->j) != CELL_FREE);
		particles[j]-> yaw = rand() % 360;
		particles[j]-> x = (particles[j]->j - (double)ogrid.getWidth() / 2) * ogrid.getResolution();
		particles[j]-> y = ((double)ogrid.getHeight() / 2 - particles[j]->i) * ogrid.getResolution();
		particles[j]->belief = computeBelief(particles[j]);
		j++;
	}

}
void LocalizationManager::getParticleIfCellNotFree(Particle *particle) {
	do{

		int angle = rand() % 360;
		int radius = rand() % RADIUS;
		particle->j = (particle->j + radius * cos (angle));
		particle->i = (particle->i + radius * sin (angle));

	} while (ogrid.getCell(particle->i, particle->j) != CELL_FREE);
	particle-> yaw = rand() % 360;
	particle-> x = (particle->j - (double)ogrid.getWidth() / 2) * ogrid.getResolution();
	particle-> y = ((double)ogrid.getHeight() / 2 - particle->i) * ogrid.getResolution();
	particle->belief = computeBelief(particle);

}




int LocalizationManager::rouletteWheel() {

	double wheel_location =0;
	double total_weight=0;
	double curr_sum;
	int index=0;
	for (int i = 1 ; i<ROULETTE ; i++) {
		total_weight += particles[PARTICLES_NUM-i]->belief;
	}
	wheel_location = (double) (rand() / (double) (RAND_MAX)) *total_weight;
	index=PARTICLES_NUM-1;
	curr_sum = particles[index]->belief;

	while (curr_sum < wheel_location && index > PARTICLES_NUM - 1 - ROULETTE ) {
		index--;
		curr_sum = curr_sum + particles[index]->belief;
	}
	//cout << "particle num " << index << endl;
	return index;

	//***********************************************
	//double temp=0;
	//for (int i =1 ; i <= 30 ;i++) {
	//	temp += particles[PARTICLES_NUM-i]->belief;
	//}

	//double value =(double) (rand() / (double) (RAND_MAX)) * temp;

	//for (int i = 1; i <= 30 ;i++) {
	//	value -= particles[PARTICLES_NUM-i]->belief;
	//	if(value <= 0) {
	//		return PARTICLES_NUM - i;
	//	}
	//}

//*************************************************************

	//total_weight <- sum_weights(particle)
	//wheel_location <- rand()*total_fitness

	//index <- 1

	//curr_sum <- weight(particles[index])

	//while (curr_sum <- wheel_location) and (index < particle_num)
	//index <- index +1
	//curr_sum <- curr_sum + weight(particles[index])
	//return particles[index]


}
void LocalizationManager::sortByBelief(){
	int n = particles.size();
	bool swapped = true;
	int j = 0;
	Particle temp;
	while (swapped) {
		swapped = false;
		j++;
		for (int i = 0; i < n - j; i++) {
			if (particles[i]->belief > particles[i + 1]->belief) {

				temp.x= particles[i]->x;
				temp.y= particles[i]->y;
				temp.i= particles[i]->i;
				temp.j= particles[i]->j;
				temp.yaw= particles[i]->yaw;
				temp.belief= particles[i]->belief;

				particles[i]->x= particles[i + 1]->x;
				particles[i]->y= particles[i + 1]->y;
				particles[i]->i= particles[i + 1]->i;
				particles[i]->j= particles[i + 1]->j;
				particles[i]->yaw= particles[i + 1]->yaw;
				particles[i]->belief= particles[i + 1]->belief;


				particles[i + 1]->x=temp.x;
				particles[i + 1]->y= temp.y;
				particles[i + 1]->i= temp.i;
				particles[i + 1]->j= temp.j;
				particles[i + 1]->yaw= temp.yaw;
				particles[i + 1]->belief= temp.belief;

				swapped = true;

			}
		}
	}
}

void LocalizationManager::getRandomLocation(Particle *particle) {
	do {
		particle->j = rand() % ogrid.getWidth();
		particle->i = rand() % ogrid.getHeight();

	} while (ogrid.getCell(particle->i, particle->j) != CELL_FREE);

	particle->x = (particle->j - (double)ogrid.getWidth() / 2) * ogrid.getResolution() ;
	particle->y = ((double)ogrid.getHeight() / 2 - particle->i) * ogrid.getResolution();
	//particle->belief = computeBelief(particle);

	//cout << "x: " << particle->x << ", y: " << particle->y << ", i: " << particle->i << ", j: " << particle->j << endl;
	particle->yaw = rand() % 360;
}

void LocalizationManager::initParticles() {
	particles.resize(PARTICLES_NUM);

	for (int i = 0; i < particles.size(); i++) {
		particles[i] = new Particle();
		getRandomLocation(particles[i]);

	}
}

double LocalizationManager::computeBelief(Particle *particle) {
	LidarScan scan = hamster->getLidarScan();

	int hits = 0;
	int misses = 0;

	for (int i = 0; i < scan.getScanSize(); i++) {
		double angle = scan.getScanAngleIncrement() * i * DEG2RAD;

		if (scan.getDistance(i) < scan.getMaxRange() - 0.001) {
			// Obstacle_Pos = Particle_Pos + Scan_Distance * cos (Heading + Scan Angle)
			double obsX = particle->x + scan.getDistance(i) * cos(angle + particle->yaw * DEG2RAD - 180 * DEG2RAD);
			double obsY = particle->y + scan.getDistance(i) * sin(angle + particle->yaw * DEG2RAD - 180 * DEG2RAD);


			//int pixelsX = obsX / ogrid.getResolution() + ogrid.getWidth() / 2;
			//int pixelsY = obsY / ogrid.getResolution() + ogrid.getHeight() / 2;

			int pixelsY = ((double)ogrid.getHeight() / 2 -(double)( obsY / ogrid.getResolution()) );
			int pixelsX = ((double)(obsX / ogrid.getResolution()) + (double)ogrid.getWidth() / 2);

			if (ogrid.getCell(pixelsY, pixelsX) == CELL_OCCUPIED) {
				hits++;
			} else {
				misses++;
			}
		}
	}
	return (float)hits / (hits + misses);
}

void LocalizationManager::updateParticles(double deltaX, double deltaY, double deltaYaw) {
	for (int i = 0; i < particles.size(); i++) {
		Particle *particle2= new Particle();
		Particle *particle = particles[i];

		particle2->x = particles[i]->x;
		particle2->y = particles[i]->y;
		particle2->yaw=particles[i]->yaw;
		particle2->i =particles[i]->i;
		particle2->j=particles[i]->j;
		particle2->belief=particles[i]->belief;

		particle->x += deltaX ;//* cos(particle->yaw * DEG2RAD);
		particle->y += deltaY ;//* sin(particle->yaw * DEG2RAD);;
		particle->yaw += deltaYaw;
		particle->i = (double)ogrid.getHeight() / 2 - particle->y / ogrid.getResolution() ;
		particle->j = particle->x / ogrid.getResolution() + ogrid.getWidth() / 2;
		particle->belief = computeBelief(particle);


		if (ogrid.getCell(particle->i, particle->j) != CELL_FREE) {
			if (particles[i]->belief < TRASHOLD ) {
				getRandomLocation(particle);
			} else {
				//getParticleIfCellNotFree(particle);
				//particles[i]->x= particle2->x;
				//particles[i]->y=particle2->y ;
				//particles[i]->yaw=particle2->yaw;
				//particles[i]->i=particle2->i ;
				//particles[i]->j=particle2->j;
				//particles[i]->belief=particle2->belief;
			}

		}
	}
	getRandomNearGoodParticles();
}

void LocalizationManager::printParticles() const {
	for (int i = 0; i < particles.size(); i++) {
		Particle *particle = particles[i];
		cout << "Particle " << i << ": " << particle->x << "," << particle->y << ", belief: " << particle->belief << endl;
	}
}

vector<Particle *> LocalizationManager::getParticles() const {
	return particles;
}



LocalizationManager::~LocalizationManager() {
	// TODO Auto-generated destructor stub
}
