import { FC } from 'react';
import { ArrowUpIcon, ArrowDownIcon } from '@heroicons/react/24/solid';

interface Stat {
  name: string;
  value: string;
  icon: React.ComponentType<React.SVGProps<SVGSVGElement>>;
  change: string;
  changeType: 'positive' | 'negative' | 'neutral';
}

interface DashboardStatsProps {
  stats: Stat[];
  isLoading: boolean;
}

const DashboardStats: FC<DashboardStatsProps> = ({ stats, isLoading }) => {
  if (isLoading) {
    return (
      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
        {[...Array(4)].map((_, index) => (
          <div key={index} className="bg-white overflow-hidden shadow-elegant rounded-lg animate-pulse">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <div className="w-8 h-8 bg-neutral-200 rounded"></div>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <div className="h-4 bg-neutral-200 rounded mb-2"></div>
                  <div className="h-6 bg-neutral-200 rounded"></div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  }

  return (
    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
      {stats.map((stat) => (
        <div key={stat.name} className="bg-white overflow-hidden shadow-elegant rounded-lg hover:shadow-luxury transition-shadow duration-200">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <stat.icon className="h-8 w-8 text-primary-600" aria-hidden="true" />
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-neutral-500 truncate">
                    {stat.name}
                  </dt>
                  <dd className="flex items-baseline">
                    <div className="text-2xl font-semibold text-neutral-900">
                      {stat.value}
                    </div>
                    <div className={`ml-2 flex items-baseline text-sm font-semibold ${
                      stat.changeType === 'positive' 
                        ? 'text-green-600' 
                        : stat.changeType === 'negative' 
                        ? 'text-red-600' 
                        : 'text-neutral-500'
                    }`}>
                      {stat.changeType === 'positive' && (
                        <ArrowUpIcon className="self-center flex-shrink-0 h-4 w-4 text-green-500" aria-hidden="true" />
                      )}
                      {stat.changeType === 'negative' && (
                        <ArrowDownIcon className="self-center flex-shrink-0 h-4 w-4 text-red-500" aria-hidden="true" />
                      )}
                      <span className="sr-only">
                        {stat.changeType === 'positive' ? 'Increased' : 'Decreased'} by
                      </span>
                      {stat.change}
                    </div>
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default DashboardStats;